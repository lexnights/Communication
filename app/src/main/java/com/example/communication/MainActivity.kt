package com.example.communication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.communication.ui.theme.CommunicationTheme
import kotlinx.serialization.json.Json
import org.publicvalue.multiplatform.qrcode.CameraPosition
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CommunicationTheme {
                //QRCodeReaderWrapper()
                FullScreenScrollableColumn {
                    BasePersonnelInfo()
                }
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
        Text(text = "$label：", style = MaterialTheme.typography.bodyMedium)
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
fun StringFormInputField(
    label: String,
    value: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FormRow(label = label, modifier = modifier) {
        UnderlinedTextField(
            value = value ?: "",
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun NumberFormInputField(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    FormRow(label = label, modifier = modifier) {
        UnderlinedTextField(
            value = value.let {
                if (it == 0) "" else it.toString()
            },
            onValueChange = {
                onValueChange(it.toIntOrNull() ?: 0)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun GenderChooseFormInputField(
    label: String,
    value: String?,
    onValueChange: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    FormRow(label = label, modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
            RadioButton(
                selected = value == "男",
                onClick = {
                    onValueChange("男")
                }
            )
            Text("男", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = value == "女",
                onClick = {
                    onValueChange("女")
                }
            )
            Text("女", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun DateChooseFormInputField(
    label: String,
    value: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FormRow(label = label, modifier = modifier) {
        UnderlinedTextField(
            value = value ?: "",
            onValueChange = onValueChange,
        )
    }
}

@Composable
fun ExpandableQRScanner(modifier: Modifier = Modifier, onScanCompleted: (String) -> Unit) {
    val qrScanned = remember { mutableStateOf(true) }
    if (qrScanned.value) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            onClick = {
                qrScanned.value = false
            }) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = ""
            )
            Text("扫码导入人员数据")
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            QRCodeReader { qrData ->
                onScanCompleted(qrData)
                qrScanned.value = true
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                onClick = {
                    qrScanned.value = true
                }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = ""
                )
                Text(text = "取消")
            }
        }
    }
}

@Composable
fun BoxWithTitleAndBorder(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            .padding(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
fun FullScreenScrollableColumn(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        content()
    }
}

@Composable
fun BasePersonnelInfo() {
    val ctx = LocalContext.current
    var personnelInfo by remember { mutableStateOf(Personnel()) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        BoxWithTitleAndBorder("基本信息") {
            ExpandableWrapControl(title = "人员数据", initiallyExpanded = true) {
                ExpandableQRScanner { qrData ->
                    try {
                        personnelInfo = Json.decodeFromString<Personnel>(qrData)
                    } catch (e: Exception) {
                        Toast.makeText(ctx, "数据解析错误", Toast.LENGTH_SHORT).show()
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                StringFormInputField("姓名",
                    value = personnelInfo.name,
                    onValueChange = { personnelInfo = personnelInfo.copy(name = it) })
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GenderChooseFormInputField(
                        "性别",
                        value = personnelInfo.gender,
                        onValueChange = { personnelInfo = personnelInfo.copy(gender = it ?: "") },
                        modifier = Modifier.weight(2f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    NumberFormInputField(
                        "年龄",
                        value = personnelInfo.age,
                        onValueChange = { personnelInfo = personnelInfo.copy(age = it) },
                        modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                StringFormInputField(
                    "部别",
                    value = personnelInfo.department,
                    onValueChange = { personnelInfo = personnelInfo.copy(department = it) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                StringFormInputField(
                    "职务",
                    value = personnelInfo.position,
                    onValueChange = { personnelInfo = personnelInfo.copy(position = it) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                StringFormInputField(
                    "军衔",
                    value = personnelInfo.rank,
                    onValueChange = { personnelInfo = personnelInfo.copy(rank = it) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                StringFormInputField(
                    "地点",
                    value = personnelInfo.hurtPlace,
                    onValueChange = { personnelInfo = personnelInfo.copy(hurtPlace = it) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                DateChooseFormInputField(
                    "时间",
                    value = personnelInfo.hurtTime,
                    onValueChange = { personnelInfo = personnelInfo.copy(hurtTime = it) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        BoxWithTitleAndBorder("其他信息") {
        }
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
fun QRCodeReader(onScanCompleted : (String) -> Unit) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        ScannerWithPermissions(
            modifier = Modifier
                .width(300.dp)
                .height(300.dp),
            onScanned = { scannedText ->
                println("Scanned: $scannedText")
                onScanCompleted(scannedText)
                true
            },
            types = listOf(CodeType.QR),
            cameraPosition = CameraPosition.BACK,
            permissionDeniedContent = {
                Text("Permission denied")
            }
        )
    }
}