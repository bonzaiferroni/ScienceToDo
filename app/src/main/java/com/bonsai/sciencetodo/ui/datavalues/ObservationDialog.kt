package com.bonsai.sciencetodo.ui.datavalues

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.bonsai.sciencetodo.ui.common.IntegerPicker
import com.bonsai.sciencetodo.ui.common.StdDialog
import java.util.Locale

@Composable
fun ObservationDialog(
    onSaveDialog: () -> Unit,
    onCancelDialog: () -> Unit,
    newDataBoxes: List<NewDataBox>?,
) {
    if (newDataBoxes == null) return

    StdDialog(
        showDialog = true,
        onDismiss = onCancelDialog,
        onAccept = onSaveDialog
    ) {
        newDataBoxes.forEach { newDataBox ->
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

    IntegerPicker(value, updateValue)
}

@Preview
@Composable
fun PreviewAddDataForm() {
    val newDataBox = FakeData.fakeVariables.filter { it.dataFlowId == 1 }.map { variable ->
        NewDataBox.getBox(variable)
    }

    ObservationDialog({}, {}, newDataBox)
}