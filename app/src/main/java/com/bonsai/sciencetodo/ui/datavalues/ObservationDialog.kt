package com.bonsai.sciencetodo.ui.datavalues

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.fakedata.FakeData
import com.bonsai.sciencetodo.ui.common.FloatField
import com.bonsai.sciencetodo.ui.common.IntegerPicker
import com.bonsai.sciencetodo.ui.common.StdDialog
import com.bonsai.sciencetodo.ui.common.StringField
import java.util.Locale

@Composable
fun ObservationDialog(
    onSaveDialog: () -> Unit,
    onCancelDialog: () -> Unit,
    newValueBoxes: List<NewValueBox>?,
) {
    if (newValueBoxes == null) return

    StdDialog(
        showDialog = true,
        onDismiss = onCancelDialog,
        onAccept = onSaveDialog
    ) {
        newValueBoxes.forEach { newDataBox ->
            val variable = newDataBox.variable

            Card {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(
                        text = variable.name.uppercase(Locale.getDefault()),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelSmall
                    )
                    when (newDataBox) {
                        is NewInteger -> {
                            IntegerSetter(onValueChange = { newDataBox.value = it })
                        }
                        is NewString -> {
                            StringSetter(onValueChange = { newDataBox.value = it })
                        }
                        is NewFloat -> {
                            FloatSetter(onValueChange = { newDataBox.value = it })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StringSetter(
    onValueChange: (String) -> Unit
) {
    var value by remember { mutableStateOf("") }
    val onUpdateValue: (String) -> Unit = {
        value = it
        onValueChange(it)
    }

    StringField(
        value = value,
        onValueChange = onUpdateValue
    )
}

@Composable
fun IntegerSetter(
    onValueChange: (Int) -> Unit
) {
    var value by remember { mutableStateOf(0) }
    val updateValue: (Int) -> Unit = {
        value = it
        onValueChange(it)
    }

    IntegerPicker(value, updateValue)
}

@Composable
fun FloatSetter(
    onValueChange: (Float) -> Unit
) {
    var value by remember { mutableStateOf("") }
    val onUpdateValue: (String) -> Unit = {
        value = it
        val convertedValue = it.toFloatOrNull()
        if (convertedValue != null) {
            onValueChange(convertedValue)
        }

    }

    FloatField(
        value = value,
        onValueChange = onUpdateValue
    )
}

@Preview
@Composable
fun PreviewAddDataForm() {
    val newValueBoxes = FakeData.fakeVariables.filter { it.dataFlowId == 1 }.map { variable ->
        NewValueBox.getBox(variable)
    }

    ObservationDialog({}, {}, newValueBoxes)
}