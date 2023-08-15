package com.bonsai.sciencetodo.ui.datavalues

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.fakedata.FakeData
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
    val focusRequesters = remember(newValueBoxes) { List(newValueBoxes.size) { FocusRequester() } }

    StdDialog(
        showDialog = true,
        onDismiss = onCancelDialog,
        onAccept = onSaveDialog,
        enableAccept = enableAccept
    ) {
        (newValueBoxes.indices).forEach { i ->
            val newValueBox = newValueBoxes[i]
            val focusRequester = focusRequesters[i]
            val nextFocusRequester = if (i < newValueBoxes.size - 1)
                focusRequesters[i + 1] else null
            val imeAction = if (i == newValueBoxes.size - 1) ImeAction.Done else ImeAction.Next
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
                            IntegerSetter(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                                focusRequester = focusRequester,
                                nextFocusRequester = nextFocusRequester,
                                imeAction = imeAction
                            )
                        }

                        is NewString -> {
                            StringSetter(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                                focusRequester = focusRequester,
                                nextFocusRequester = nextFocusRequester,
                                imeAction = imeAction
                            )
                        }

                        is NewFloat -> {
                            FloatSetter(
                                onValueChange = {
                                    newValueBox.value = it
                                    enableAccept = newValueBoxes.isValid()
                                },
                                focusRequester = focusRequester,
                                nextFocusRequester = nextFocusRequester,
                                imeAction = imeAction
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}

@Composable
fun StringSetter(
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?,
    imeAction: ImeAction
) {
    var value by remember { mutableStateOf("") }
    val onUpdateValue: (String) -> Unit = {
        value = it
        onValueChange(it)
    }

    StringField(
        value = value,
        onValueChange = onUpdateValue,
        focusRequester = focusRequester,
        nextFocusRequester = nextFocusRequester,
        imeAction = imeAction
    )
}

@Composable
fun IntegerSetter(
    onValueChange: (Int?) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?,
    imeAction: ImeAction
) {
    var value by remember { mutableStateOf("") }
    val updateValue: (String) -> Unit = {
        if (!it.contains('.')) {
            value = it
            onValueChange(it.toIntOrNull())
        }
    }

    IntegerField(
        value,
        updateValue,
        focusRequester = focusRequester,
        nextFocusRequester = nextFocusRequester,
        imeAction = imeAction
    )
}

@Composable
fun FloatSetter(
    onValueChange: (Float?) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?,
    imeAction: ImeAction
) {
    var value by remember { mutableStateOf("") }
    val onUpdateValue: (String) -> Unit = {
        value = it
        onValueChange(it.toFloatOrNull())
    }

    FloatField(
        value = value,
        onValueChange = onUpdateValue,
        focusRequester = focusRequester,
        nextFocusRequester = nextFocusRequester,
        imeAction = imeAction
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