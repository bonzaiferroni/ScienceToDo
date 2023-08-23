package com.bonsai.sciencetodo.ui.datavalues

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
import com.bonsai.sciencetodo.data.NewBoolean
import com.bonsai.sciencetodo.data.NewEnum
import com.bonsai.sciencetodo.data.NewFloat
import com.bonsai.sciencetodo.data.NewInteger
import com.bonsai.sciencetodo.data.NewString
import com.bonsai.sciencetodo.data.NewValueBox
import com.bonsai.sciencetodo.data.fake.FakeData
import com.bonsai.sciencetodo.data.isValid
import com.bonsai.sciencetodo.ui.common.BooleanField
import com.bonsai.sciencetodo.ui.common.EnumField
import com.bonsai.sciencetodo.ui.common.FloatField
import com.bonsai.sciencetodo.ui.common.IntegerField
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

    var enableAccept by remember { mutableStateOf(false) }

    StdDialog(
        showDialog = true,
        onDismiss = onCancelDialog,
        onAccept = onSaveDialog,
        enableAccept = enableAccept
    ) {
        newValueBoxes.forEach { newValueBox ->
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
                    when (newValueBox) {
                        is NewInteger -> {
                            IntegerField(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                            )
                        }

                        is NewString -> {
                            StringField(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                            )
                        }

                        is NewFloat -> {
                            FloatField(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                            )
                        }

                        is NewBoolean -> {
                            BooleanField(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                            )
                        }

                        is NewEnum -> {
                            EnumField(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddDataForm() {
    val newValueBoxes = FakeData.fakeVariables.filter { it.datasetId == 1 }.map { variable ->
        NewValueBox.getBox(variable)
    }

    ObservationDialog({}, {}, newValueBoxes)
}