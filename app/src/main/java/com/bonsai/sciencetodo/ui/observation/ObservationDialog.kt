package com.bonsai.sciencetodo.ui.observation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.bonsai.sciencetodo.ui.common.StdDialog
import java.util.Locale

@Composable
fun ObservationDialog(
    onSaveDialog: () -> Unit,
    onCancelDialog: () -> Unit,
    newValues: List<NewValue>?,
) {
    if (newValues == null) return

    var enableAccept by remember { mutableStateOf(false) }

    StdDialog(
        showDialog = true,
        onDismiss = onCancelDialog,
        onAccept = onSaveDialog,
        enableAccept = enableAccept
    ) {
        newValues.forEach { newValueBox ->
            val variable = newValueBox.variable

            Surface(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.extraLarge
            ) {
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
                    /*when (newValueBox) {
                        is NewInteger -> {
                            com.bonsai.sciencetodo.ui.observation.IntegerField(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                            )
                        }

                        is NewString -> {
                            com.bonsai.sciencetodo.ui.observation.StringField(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                            )
                        }

                        is NewFloat -> {
                            com.bonsai.sciencetodo.ui.observation.FloatField(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                            )
                        }

                        is NewBoolean -> {
                            com.bonsai.sciencetodo.ui.observation.BooleanField(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                            )
                        }

                        is NewEnum -> {
                            com.bonsai.sciencetodo.ui.observation.EnumField(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                            )
                        }
                    }*/
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddDataForm() {
    /*val newValueBoxes = FakeData.fakeVariables.filter { it.datasetId == 1 }.map { variable ->
        NewValue.getBox(variable) { }
    }

    ObservationDialog({}, {}, newValueBoxes)*/
}