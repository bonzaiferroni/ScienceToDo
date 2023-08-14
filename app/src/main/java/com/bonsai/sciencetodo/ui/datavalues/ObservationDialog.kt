package com.bonsai.sciencetodo.ui.datavalues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.fakedata.FakeData
import com.bonsai.sciencetodo.model.VariableType
import com.bonsai.sciencetodo.ui.common.IntegerPicker
import com.bonsai.sciencetodo.ui.common.StdDialog
import java.util.Locale

@Composable
fun ObservationDialog(
    onSaveDialog: () -> Unit,
    onCancelDialog: () -> Unit,
    newDataValues: List<NewDataValue>?,
) {
    if (newDataValues == null) return

    StdDialog(
        showDialog = true,
        onDismiss = onCancelDialog,
        onAccept = onSaveDialog
    ) {
        newDataValues.forEach { updater ->
            val variable = updater.variable

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
                    if (variable.type == VariableType.Integer) {
                        New_IntegerSetter(onValueChange = { updater.value = it })
                    } else if (variable.type == VariableType.String) {
                        StringSetter(onValueChange = { updater.value = it })
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
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(text)
        },
        label = { Text("Enter text") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                // Handle onDone action if needed
            }
        )
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
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = { if (value > 0) updateValue(value - 1) }) {
            Text("-")
        }
        Text(text = value.toString())
        TextButton(onClick = { updateValue(value + 1) }) {
            Text("+")
        }
    }
}

@Composable
fun New_IntegerSetter(
    onValueChange: (Int) -> Unit
) {
    var value by remember { mutableStateOf(0) }
    val updateValue: (Int) -> Unit = {
        value = it
        onValueChange(it)
    }

    IntegerPicker(value, updateValue)
}

@Preview
@Composable
fun PreviewAddDataForm() {
    val updaters = FakeData.fakeVariables.filter { it.dataFlowId == 1 }.map { variable ->
        NewDataValue(variable)
    }

    ObservationDialog({}, {}, updaters)
}