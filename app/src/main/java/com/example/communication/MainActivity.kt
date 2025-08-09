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
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.communication.ui.theme.CommunicationTheme
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.serialization.json.Json
import org.publicvalue.multiplatform.qrcode.CameraPosition
import org.publicvalue.multiplatform.qrcode.CodeType
import org.publicvalue.multiplatform.qrcode.ScannerWithPermissions
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)

        enableEdgeToEdge()
        setContent {
            CommunicationTheme {
                //QRCodeReaderWrapper()
                var personnelTicketInfo by remember { mutableStateOf(PersonnelTicket()) }
                FullScreenScrollableColumn {
                    BasePersonnelInfo(
                        personnelTicketInfo = personnelTicketInfo,
                        onPersonnelInfoChange = {
                            personnelTicketInfo = it
                        }
                    )
                    InjureDetailInfo(
                        personnelTicketInfo = personnelTicketInfo,
                        onPersonnelInfoChange = {
                            personnelTicketInfo = it
                        }
                    )
                    CounteractDetailInfo(
                        personnelTicketInfo = personnelTicketInfo,
                        onPersonnelInfoChange = {
                            personnelTicketInfo = it
                        }
                    )
                    EvacuateDetailInfo(
                        personnelTicketInfo = personnelTicketInfo,
                        onPersonnelInfoChange = {
                            personnelTicketInfo = it
                        }
                    )
                    SurgeonDetailInfo(
                        personnelTicketInfo = personnelTicketInfo,
                        onPersonnelInfoChange = {
                            personnelTicketInfo = it
                        }
                    )
                    Text("Debug:$personnelTicketInfo")
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
fun YesNoChooseFormInputField(
    label: String,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    FormRow(label = label, modifier = modifier) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = value,
                        onClick = { onValueChange(true) },
                        role = Role.RadioButton
                    )
            ) {
                RadioButton(
                    selected = value,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        // todo: 之后改颜色
                    )
                )
                Text(
                    text = "是",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 2.dp, end = 6.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = !value,
                        onClick = { onValueChange(false) },
                        role = Role.RadioButton
                    )
            ) {
                RadioButton(
                    selected = !value,
                    onClick = null
                )
                Text(
                    text = "否",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
        }
    }
}

@Composable
fun AidTypeFormInputField(
    value: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val aidTypes = listOf("自救", "互救", "卫救", "未包扎")
    FormRow(label = "救治措施", modifier = modifier) {
        FlowRow(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.End,
            maxItemsInEachRow = 2
        ) {
            aidTypes.forEach { type ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .selectable(
                            selected = value == type,
                            onClick = { onValueChange(type) },
                            role = Role.RadioButton
                        )
                        //.weight(1f)
                ) {
                    RadioButton(
                        selected = value == type,
                        onClick = null,
                        colors = RadioButtonDefaults.colors()
                    )
                    Text(
                        text = type,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 2.dp, end = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SingleChoiceFormInputField(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    enableOtherOption: Boolean = false
) {
    SubRegionWithTitle(title = label) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = option == selectedOption,
                            onClick = { onOptionSelected(option) },
                            role = Role.RadioButton
                        )
                ) {
                    RadioButton(
                        selected = option == selectedOption,
                        onClick = null
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }

            if (enableOtherOption) {
                val otherOption = remember { mutableStateOf("") }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = otherOption.value == selectedOption,
                            onClick = { onOptionSelected(otherOption.value) },
                            role = Role.RadioButton
                        )
                ) {
                    RadioButton(
                        selected = otherOption.value == selectedOption,
                        onClick = null
                    )
                    Text(
                        text = "其他",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                    UnderlinedTextField(
                        value = otherOption.value,
                        onValueChange = {
                            if (otherOption.value == selectedOption) {
                                onOptionSelected(it)
                            }

                            otherOption.value = it
                        },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                        modifier = Modifier.width(120.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MultipleChoiceFormInputField(
    label: String,
    options: Array<String>,
    selectedOptions: Array<String>?,
    onSelectionChange: (Array<String>?) -> Unit,
    enableOtherOption: Boolean = false
) {
    SubRegionWithTitle(title = label) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val selected = selectedOptions?.contains(option) ?: false

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = selected,
                            onClick = {
                                val newSelection =
                                    selectedOptions?.toMutableList() ?: mutableListOf()

                                if (selected) {
                                    newSelection.remove(option)
                                } else {
                                    newSelection.add(option)
                                }

                                onSelectionChange(newSelection.toTypedArray())
                            },
                            role = Role.Checkbox
                        )
                ) {
                    Checkbox(
                        checked = selected,
                        onCheckedChange = null,
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }

            if (enableOtherOption) {
                val otherOption = remember { mutableStateOf("") }
                val isOtherOptionSelected = selectedOptions?.contains(otherOption.value) ?: false
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = isOtherOptionSelected,
                            onClick = {
                                val newSelection =
                                    selectedOptions?.toMutableList() ?: mutableListOf()

                                if (isOtherOptionSelected) {
                                    newSelection.remove(otherOption.value)
                                } else {
                                    newSelection.add(otherOption.value)
                                }

                                onSelectionChange(newSelection.toTypedArray())
                            },
                            role = Role.RadioButton
                        )
                ) {
                    Checkbox(
                        checked = isOtherOptionSelected,
                        onCheckedChange = null,
                    )
                    Text(
                        text = "其他",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                    UnderlinedTextField(
                        value = otherOption.value,
                        onValueChange = {
                            if (isOtherOptionSelected) {
                                val newSelection = selectedOptions.toMutableList()
                                newSelection.remove(otherOption.value)

                                otherOption.value = it
                                newSelection.add(otherOption.value)

                                onSelectionChange(newSelection.toTypedArray())
                            } else {
                                otherOption.value = it
                            }
                        },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                        modifier = Modifier.width(120.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleDatePickerDialog(
    isDatePickerVisible: Boolean,
    onDialogVisibilityChanged: (Boolean) -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onDateSelected: (String) -> Unit = {}
) {
    val current = LocalDateTime.now()

    val year = remember { mutableIntStateOf(current.year) }
    val month = remember { mutableIntStateOf(current.monthValue) }
    val day = remember { mutableIntStateOf(current.dayOfMonth) }
    val hour = remember { mutableIntStateOf(current.hour) }

    if (isDatePickerVisible) {
        Dialog(
            onDismissRequest = {
                onDismissRequest()
                onDialogVisibilityChanged(false)
            }
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    SubRegionWithTitle("选择日期") {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            UnderlinedTextField(
                                value = year.intValue.let {
                                    if (it == -1) "" else it.toString()
                                },
                                onValueChange = {
                                    year.intValue = it.toIntOrNull() ?: -1
                                },
                                textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier.width(60.dp)
                            )
                            Text(
                                text = "年",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            UnderlinedTextField(
                                value = month.intValue.let {
                                    if (it == -1) "" else it.toString()
                                },
                                onValueChange = {
                                    month.intValue = it.toIntOrNull() ?: -1
                                },
                                textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier.width(30.dp)
                            )
                            Text(
                                text = "月",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            UnderlinedTextField(
                                value = day.intValue.let {
                                    if (it == -1) "" else it.toString()
                                },
                                onValueChange = {
                                    day.intValue = it.toIntOrNull() ?: -1
                                },
                                textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier.width(30.dp)
                            )
                            Text(
                                text = "日",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            UnderlinedTextField(
                                value = hour.intValue.let {
                                    if (it <= -1 || it >= 24) "" else it.toString()
                                },
                                onValueChange = {
                                    hour.intValue = it.toIntOrNull() ?: -1
                                },
                                textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                                modifier = Modifier.width(30.dp)
                            )
                            Text(
                                text = "时",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                onDismissRequest()
                                onDialogVisibilityChanged(false)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "取消"
                            )
                            Text("取消")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                onDateSelected(
                                    "${year.intValue}年${month.intValue}月${day.intValue}日${hour.intValue}时"
                                )
                                onDialogVisibilityChanged(false)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Blue,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "确定"
                            )
                            Text("确定")
                        }
                    }
                }
            }
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
    val isDatePickerVisible = remember { mutableStateOf(false) }

    FormRow(label = label, modifier = modifier) {
        UnderlinedTextField(
            value = value ?: "",
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            "选择日期",
            modifier = Modifier
                .clickable {
                    isDatePickerVisible.value = true
                }
                .weight(0.5f),
            color = Color.Blue,
            style = MaterialTheme.typography.bodyMedium,
            textDecoration = TextDecoration.Underline
        )
    }

    if (isDatePickerVisible.value) {
        SimpleDatePickerDialog(
            isDatePickerVisible = isDatePickerVisible.value,
            onDialogVisibilityChanged = {
                isDatePickerVisible.value = it
            },
            onDismissRequest = {
                isDatePickerVisible.value = false
            },
            onDateSelected = onValueChange
        )
    }
}

@Composable
fun SimpleDrugDosageInputField(
    drugName: String,
    drugUnit: String,
    drugDosage: Int,
    onDrugDosageChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = drugName,
            style = MaterialTheme.typography.bodyMedium,
        )
        UnderlinedTextField(
            value = drugDosage.let {
                if (it == -1) ""
                else it.toString()
            },
            onValueChange = {
                val newDosage = it.toIntOrNull() ?: -1
                onDrugDosageChange(newDosage)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = drugUnit,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun MultipleDrugDosageInputField(
    injectedDrugs: Map<String, String>?,
    onInjectedDrugsChange: (Map<String, String>) -> Unit,
    modifier: Modifier = Modifier
) {
    injectedDrugs?.forEach { (drugName, drugDosage) ->
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "药名",
                style = MaterialTheme.typography.bodyMedium,
            )
            UnderlinedTextField(
                value = drugName,
                onValueChange = { newDrugName ->
                    val updatedMap = injectedDrugs.toMutableMap()
                    updatedMap.remove(drugName)
                    updatedMap[newDrugName] = drugDosage
                    onInjectedDrugsChange(updatedMap)
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "，剂量",
                style = MaterialTheme.typography.bodyMedium
            )
            UnderlinedTextField(
                value = drugDosage,
                onValueChange = {
                    val updatedMap = injectedDrugs.toMutableMap()
                    updatedMap[drugName] = it
                    onInjectedDrugsChange(updatedMap)
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                modifier = Modifier.width(100.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    if (injectedDrugs == null || injectedDrugs.keys.last() != "") {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "+ 添加更多药物",
                modifier = Modifier
                    .clickable {
                        val updatedMap = injectedDrugs?.toMutableMap() ?: mutableMapOf()
                        updatedMap.put("", "")
                        onInjectedDrugsChange(updatedMap)
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                color = Color.Blue,
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
fun TransfusionDosageInputField(
    transfusion: Pair<String, Int>?,
    onTransfusionChange: (Pair<String, Int>?) -> Unit,
    modifier: Modifier = Modifier
) {
    val (bloodType, dosage) = transfusion ?: Pair("", 0)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "输血（血型",
            style = MaterialTheme.typography.bodyMedium,
        )
        UnderlinedTextField(
            value = bloodType,
            onValueChange = { newBloodType ->
                onTransfusionChange(Pair(newBloodType, dosage))
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            modifier = Modifier.width(30.dp)
        )
        Text(
            text = "），",
            style = MaterialTheme.typography.bodyMedium
        )
        UnderlinedTextField(
            value = dosage.toString(),
            onValueChange = { newDosage ->
                onTransfusionChange(Pair(bloodType, newDosage.toIntOrNull() ?: 0))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = "毫升",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun InjectionDosageInputField(
    injection: Pair<String, Int>?,
    onInjectionChange: (Pair<String, Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    val (drugName, dosage) = injection ?: Pair("", 0)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "输液  名称",
            style = MaterialTheme.typography.bodyMedium,
        )
        UnderlinedTextField(
            value = drugName,
            onValueChange = { newDrugName ->
                onInjectionChange(Pair(newDrugName, dosage))
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            modifier = Modifier.width(50.dp)
        )
        Text(
            text = "剂量",
            style = MaterialTheme.typography.bodyMedium
        )
        UnderlinedTextField(
            value = dosage.toString(),
            onValueChange = { newDosage ->
                onInjectionChange(Pair(drugName, newDosage.toIntOrNull() ?: 0))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            modifier = Modifier.width(50.dp)
        )
        Text(
            text = "毫升",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun PainkillerDosageInputField(
    painkiller: Triple<String, Int, String>?,
    onPainkillerChange: (Triple<String, Int, String>?) -> Unit,
    modifier: Modifier = Modifier
) {
    val (painkillerName, dosage, time) = painkiller ?: Triple("", 0, "")
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "止痛  药名",
            style = MaterialTheme.typography.bodyMedium,
        )
        UnderlinedTextField(
            value = painkillerName,
            onValueChange = { newPainkillerName ->
                onPainkillerChange(Triple(newPainkillerName, dosage, time))
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            modifier = Modifier.width(50.dp)
        )
        Text(
            text = "剂量",
            style = MaterialTheme.typography.bodyMedium
        )
        UnderlinedTextField(
            value = dosage.toString(),
            onValueChange = { newDosage ->
                onPainkillerChange(Triple(painkillerName, newDosage.toIntOrNull() ?: 0, time))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            modifier = Modifier.width(50.dp)
        )
        Text(
            text = "时间",
            style = MaterialTheme.typography.bodyMedium
        )
        UnderlinedTextField(
            value = time,
            onValueChange = { newTime ->
                onPainkillerChange(Triple(painkillerName, dosage, newTime))
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
            modifier = Modifier.width(50.dp)
        )
    }
}

@Composable
fun ExpandableQRScanner(
    isQRScannerVisible: Boolean,
    onQRScannerVisibilityChanged: (Boolean) -> Unit = {},
    onScanCompleted: (String) -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White
        ),
        onClick = {
            onQRScannerVisibilityChanged(true)
        }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = ""
        )
        Text("扫码导入人员数据")
    }

    if (isQRScannerVisible) {
        Dialog(onDismissRequest = {
                onQRScannerVisibilityChanged(false)
            },
        ) {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = Color.White
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    QRCodeReader { qrData ->
                        onScanCompleted(qrData)
                        onQRScannerVisibilityChanged(false)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        onClick = {
                            onQRScannerVisibilityChanged(false)
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
fun SubRegionWithTitle(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(4.dp),
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

fun generatePersonnelArrivalTimeString(): String? {
    val currentTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时")
    return currentTime.format(formatter)
}

@Composable
fun BasePersonnelInfo(
    personnelTicketInfo: PersonnelTicket,
    onPersonnelInfoChange: (PersonnelTicket) -> Unit
) {
    val ctx = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 0.dp
            )
    ) {
        BoxWithTitleAndBorder("基本信息") {
            ExpandableWrapControl(title = "人员数据", initiallyExpanded = true) {
                val isQRScannerVisible = remember { mutableStateOf(false) }
                ExpandableQRScanner(
                    isQRScannerVisible = isQRScannerVisible.value,
                    onQRScannerVisibilityChanged = { isQRScannerVisible.value = it }
                ) { qrData ->
                    try {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                personnel = Json.decodeFromString<Personnel>(qrData),
                                arriveTime = generatePersonnelArrivalTimeString()
                            )
                        )
                    } catch (e: Exception) {
                        println(e.toString())
                        Toast.makeText(ctx, "数据解析错误", Toast.LENGTH_SHORT).show()
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                StringFormInputField("姓名",
                    value = personnelTicketInfo.personnel.name,
                    onValueChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(personnel = personnelTicketInfo.personnel.copy(name = it))
                        )
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GenderChooseFormInputField(
                        "性别",
                        value = personnelTicketInfo.personnel.gender,
                        onValueChange = {
                            onPersonnelInfoChange(
                                personnelTicketInfo.copy(personnel = personnelTicketInfo.personnel.copy(gender = it ?: ""))
                            )
                        },
                        modifier = Modifier.weight(2f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    NumberFormInputField(
                        "年龄",
                        value = personnelTicketInfo.personnel.age,
                        onValueChange = {
                            onPersonnelInfoChange(
                                personnelTicketInfo.copy(personnel = personnelTicketInfo.personnel.copy(age = it))
                            )
                        },
                        modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                StringFormInputField(
                    "部别",
                    value = personnelTicketInfo.personnel.department,
                    onValueChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(personnel = personnelTicketInfo.personnel.copy(department = it))
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                StringFormInputField(
                    "职务",
                    value = personnelTicketInfo.personnel.job,
                    onValueChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(personnel = personnelTicketInfo.personnel.copy(job = it))
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                StringFormInputField(
                    "军衔",
                    value = personnelTicketInfo.personnel.rank,
                    onValueChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(personnel = personnelTicketInfo.personnel.copy(rank = it))
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                StringFormInputField(
                    "负伤地点",
                    value = personnelTicketInfo.hurtPlace,
                    onValueChange = { onPersonnelInfoChange(personnelTicketInfo.copy(hurtPlace = it)) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                DateChooseFormInputField(
                    "负伤时间",
                    value = personnelTicketInfo.hurtTime,
                    onValueChange = { onPersonnelInfoChange(personnelTicketInfo.copy(hurtTime = it)) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                DateChooseFormInputField(
                    "到达时间",
                    value = personnelTicketInfo.arriveTime,
                    onValueChange = { onPersonnelInfoChange(personnelTicketInfo.copy(arriveTime = it)) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ExpandableWrapControl(title = "补充信息", initiallyExpanded = true) {
                YesNoChooseFormInputField(
                    "是否为战伤",
                    value = personnelTicketInfo.type,
                    onValueChange = { onPersonnelInfoChange(personnelTicketInfo.copy(type = it)) }
                )
                AidTypeFormInputField(
                    value = personnelTicketInfo.aid ?: "卫救",
                    onValueChange = { onPersonnelInfoChange(personnelTicketInfo.copy(aid = it)) }
                )
                YesNoChooseFormInputField(
                    "紧急救治",
                    value = personnelTicketInfo.emergencyTreatment,
                    onValueChange = { onPersonnelInfoChange(personnelTicketInfo.copy(emergencyTreatment = it)) }
                )
                YesNoChooseFormInputField(
                    "放射性污染",
                    value = personnelTicketInfo.radioactive,
                    onValueChange = { onPersonnelInfoChange(personnelTicketInfo.copy(radioactive = it)) }
                )
                YesNoChooseFormInputField(
                    "隔离",
                    value = personnelTicketInfo.isolation,
                    onValueChange = { onPersonnelInfoChange(personnelTicketInfo.copy(isolation = it)) }
                )
                YesNoChooseFormInputField(
                    "毒剂",
                    value = personnelTicketInfo.poison,
                    onValueChange = { onPersonnelInfoChange(personnelTicketInfo.copy(poison = it)) }
                )
            }
        }
    }
}

@Composable
fun InjureDetailInfo(
    personnelTicketInfo: PersonnelTicket,
    onPersonnelInfoChange: (PersonnelTicket) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 0.dp
            )
    ) {
        BoxWithTitleAndBorder("战伤分类") {
            MultipleChoiceFormInputField(
                "伤部",
                options = arrayOf(
                    "颅脑", "颌面", "颈", "胸(背)", "腹(腰)",
                    "骨盆(会阴)", "脊柱", "上肢", "下肢", "内脏"
                ),
                selectedOptions = personnelTicketInfo.injuredArea,
                onSelectionChange = {
                    onPersonnelInfoChange(personnelTicketInfo.copy(injuredArea = it))
                }
            )
            MultipleChoiceFormInputField(
                "伤类",
                options = arrayOf(
                    "弹片伤", "地雷伤", "枪弹伤", "烧伤",
                    "刃器伤", "挤压伤", "冻伤", "冲击伤",
                    "毒剂伤", "核放射伤害"
                ),
                selectedOptions = personnelTicketInfo.injuredType,
                onSelectionChange = {
                    onPersonnelInfoChange(personnelTicketInfo.copy(injuredType = it))
                },
                enableOtherOption = true
            )
            MultipleChoiceFormInputField(
                "伤情",
                options = arrayOf(
                    "大出血", "窒息", "休克", "昏迷",
                    "气胸", "骨折", "截瘫"
                ),
                selectedOptions = personnelTicketInfo.injuredCondition,
                onSelectionChange = {
                    onPersonnelInfoChange(personnelTicketInfo.copy(injuredCondition = it))
                },
                enableOtherOption = true
            )
            MultipleChoiceFormInputField(
                "伤型",
                options = arrayOf(
                    "贯通", "盲管", "切线", "闭合"
                ),
                selectedOptions = personnelTicketInfo.woundType,
                onSelectionChange = {
                    onPersonnelInfoChange(personnelTicketInfo.copy(woundType = it))
                },
                enableOtherOption = true
            )
            SingleChoiceFormInputField(
                "伤势",
                options = listOf("轻", "中", "重"),
                selectedOption = personnelTicketInfo.injuredSeverity,
                onOptionSelected = { onPersonnelInfoChange(personnelTicketInfo.copy(injuredSeverity = it)) }
            )
        }
    }
}

@Composable
fun CounteractDetailInfo(
    personnelTicketInfo: PersonnelTicket,
    onPersonnelInfoChange: (PersonnelTicket) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 0.dp
            )
    ) {
        BoxWithTitleAndBorder("处置措施") {
            SubRegionWithTitle("抗感染") {
                SimpleDrugDosageInputField(
                    drugName = "破伤风类毒素",
                    drugUnit = "毫升",
                    drugDosage = personnelTicketInfo.toxoid,
                    onDrugDosageChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                toxoid = it
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                SimpleDrugDosageInputField(
                    drugName = "破伤风抗毒血清",
                    drugUnit = "单位",
                    drugDosage = personnelTicketInfo.tetanus,
                    onDrugDosageChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                tetanus = it
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                MultipleDrugDosageInputField(
                    injectedDrugs = personnelTicketInfo.injectedDrugs,
                    onInjectedDrugsChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                injectedDrugs = it
                            )
                        )
                    }
                )
            }

            SubRegionWithTitle(title = "抗休克") {
                TransfusionDosageInputField(
                    transfusion = personnelTicketInfo.transfusion,
                    onTransfusionChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                transfusion = it
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InjectionDosageInputField(
                    injection = personnelTicketInfo.injection,
                    onInjectionChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                injection = it
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                PainkillerDosageInputField(
                    painkiller = personnelTicketInfo.painKillers,
                    onPainkillerChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                painKillers = it
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                YesNoChooseFormInputField(
                    "吸氧",
                    value = personnelTicketInfo.oxygenInhalation,
                    onValueChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                oxygenInhalation = it
                            )
                        )
                    }
                )
                YesNoChooseFormInputField(
                    "抗休克裤",
                    value = personnelTicketInfo.antiShockPants,
                    onValueChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                antiShockPants = it
                            )
                        )
                    }
                )
                StringFormInputField(
                    "其他措施",
                    value = personnelTicketInfo.shockOther,
                    onValueChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                shockOther = it
                            )
                        )
                    }
                )
            }
            MultipleChoiceFormInputField(
                "紧急手术",
                options = arrayOf(
                    "气管切开", "血管结扎", "开放气胸封闭",
                    "血气胸闭式引流", "导尿", "耻骨上膀胱穿刺"
                ),
                selectedOptions = personnelTicketInfo.emergencySurgery,
                onSelectionChange = {
                    onPersonnelInfoChange(
                        personnelTicketInfo.copy(
                            emergencySurgery = it
                        )
                    )
                },
                enableOtherOption = true
            )
        }
    }
}

@Composable
fun EvacuateDetailInfo(
    personnelTicketInfo: PersonnelTicket,
    onPersonnelInfoChange: (PersonnelTicket) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 0.dp
            )
    ) {
        BoxWithTitleAndBorder("后送") {
            SubRegionWithTitle("时间") {
                DateChooseFormInputField(
                    "于",
                    value = personnelTicketInfo.evacuateTime,
                    onValueChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                evacuateTime = it
                            )
                        )
                    }
                )
                StringFormInputField(
                    "送往",
                    value = personnelTicketInfo.evacuateDestination,
                    onValueChange = {
                        onPersonnelInfoChange(
                            personnelTicketInfo.copy(
                                evacuateDestination = it
                            )
                        )
                    }
                )
            }
            SingleChoiceFormInputField(
                "方式",
                options = listOf(
                    "步行", "担架", "汽车", "救护车",
                    "直升机", "船", "回程汽车"
                ),
                selectedOption = personnelTicketInfo.evacuateVehicle,
                onOptionSelected = {
                    onPersonnelInfoChange(
                        personnelTicketInfo.copy(
                            evacuateVehicle = it
                        )
                    )
                },
                enableOtherOption = true
            )
            SingleChoiceFormInputField(
                "体位",
                options = listOf("坐", "半卧", "卧", "侧卧(左右)"),
                selectedOption = personnelTicketInfo.restingPosition,
                onOptionSelected = {
                    onPersonnelInfoChange(
                        personnelTicketInfo.copy(
                            restingPosition = it
                        )
                    )
                },
                enableOtherOption = true
            )
        }
    }
}

@Composable
fun SurgeonDetailInfo(
    personnelTicketInfo: PersonnelTicket,
    onPersonnelInfoChange: (PersonnelTicket) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 0.dp
            )
    ) {
        BoxWithTitleAndBorder("填写者信息") {
            StringFormInputField(
                "军医",
                value = personnelTicketInfo.surgeon,
                onValueChange = {
                    onPersonnelInfoChange(
                        personnelTicketInfo.copy(
                            surgeon = it
                        )
                    )
                }
            )
            StringFormInputField(
                "填表部门",
                value = personnelTicketInfo.fillDepartment,
                onValueChange = {
                    onPersonnelInfoChange(
                        personnelTicketInfo.copy(
                            fillDepartment = it
                        )
                    )
                }
            )
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
            FlowColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
                content()
            }
        }
    }
}


@Composable
fun QRCodeReader(onScanCompleted : (String) -> Unit) {
    Box(
        modifier = Modifier
            .size(270.dp)
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