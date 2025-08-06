package com.example.communication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.communication.ui.theme.CommunicationTheme
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.Scanner

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CommunicationTheme {
                QRCodeReaderView()
            }
        }
    }
}

@Composable
fun QRCodeReaderView() {
    Scanner(
        onScanned = { scannedText ->
            println("Scanned: $scannedText")
            false
        },
        types = listOf(CodeType.QR)
    )
}