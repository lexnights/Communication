package com.example.communication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.communication.ui.theme.CommunicationTheme
import org.publicvalue.multiplatform.qrcode.CodeType

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import org.publicvalue.multiplatform.qrcode.CameraPosition
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions
import androidx.compose.ui.draw.clipToBounds


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CommunicationTheme {
                //QRCodeReaderWrapper()
                BasePersonnelInfo()
            }
        }
    }
}

@Composable
fun FormRow(label: String, modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$label：")
        Spacer(modifier = Modifier.width(8.dp))
        content()
    }
}

@Composable
fun UnderlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true
) {
    Column(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
    }
}

@Composable
fun StringFormInputField(label: String, modifier: Modifier = Modifier) {
    val textState = remember { mutableStateOf("") }
    FormRow(label = label, modifier = modifier) {
        UnderlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun NumberFormInputField(label: String, modifier: Modifier = Modifier) {
    val numberState = remember { mutableStateOf("") }
    FormRow(label = label, modifier = modifier) {
        UnderlinedTextField(
            value = numberState.value,
            onValueChange = { numberState.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun GenderChooseFormInputField(label: String, modifier: Modifier = Modifier) {
    val selectedGender = remember { mutableStateOf<String?>(null) }
    FormRow(label = label, modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
            RadioButton(
                selected = selectedGender.value == "男",
                onClick = { selectedGender.value = "男" }
            )
            Text("男")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = selectedGender.value == "女",
                onClick = { selectedGender.value = "女" }
            )
            Text("女")
        }
    }
}

@Composable
fun DateChooseFormInputField(label: String) {
    val dateTimeState = remember { mutableStateOf("") }
    FormRow(label = label) {
        UnderlinedTextField(
            value = dateTimeState.value,
            onValueChange = { dateTimeState.value = it },
        )
    }
}

@Composable
fun BasePersonnelInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ExpandableWrapControl(title = "人员信息") {
            StringFormInputField("姓名")
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GenderChooseFormInputField("性别", modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                NumberFormInputField("年龄", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(8.dp))
            StringFormInputField("部别")
            Spacer(modifier = Modifier.height(8.dp))
            StringFormInputField("职务")
            Spacer(modifier = Modifier.height(8.dp))
            StringFormInputField("地点")
            Spacer(modifier = Modifier.height(8.dp))
            DateChooseFormInputField("时间")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("其他信息")
    }
}

@Composable
fun ExpandableWrapControl(
    modifier: Modifier = Modifier,
    title: String = "Expandable Content",
    initiallyExpanded: Boolean = false,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(initiallyExpanded) }

    Column(modifier = modifier.animateContentSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Icon(
                imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = if (expanded) "Collapse" else "Expand"
            )
        }

        AnimatedVisibility(visible = expanded) {
            // todo: 之后用 FlowRow 之类的玩意，要装依赖所以我懒了
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                content()
            }
        }
    }
}


@Composable
fun QRCodeReader(rememberedValue : MutableState<String>) {
    ScannerWithPermissions (
        modifier = Modifier.clipToBounds(),
        onScanned = { scannedText ->
            println("Scanned: $scannedText");
            rememberedValue.value = scannedText
            true
        },
        types = listOf(CodeType.QR),
        cameraPosition = CameraPosition.BACK,
        permissionDeniedContent = {
            Text("Permission denied")
        }
    )
}

@Composable
fun QRCodeReaderWrapper() {
    val scanned = remember { mutableStateOf("") }
    QRCodeReader(scanned)
    if (scanned.value.isNotEmpty()) {
        Text(text = scanned.value)
    }
}