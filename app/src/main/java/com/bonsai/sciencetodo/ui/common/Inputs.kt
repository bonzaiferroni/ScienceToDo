package com.bonsai.sciencetodo.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bonsai.sciencetodo.R
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun IntegerPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var possibleValues: Iterable<Int> by remember { mutableStateOf((-10..10).map { it * 10 }) }
    val incrementValues = listOf(1, 10, 100, 1000)
    var incrementValue by remember { mutableStateOf(incrementValues[1]) }

    val updateIncrementValue: (Int) -> Unit = { newIncrementValue ->
        incrementValue = newIncrementValue
        possibleValues = (-10..10).map { it * incrementValue + value }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(text = "Value".uppercase(), fontSize = 10.sp)

        NumberPicker(
            value = value,
            range = possibleValues,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground
            ),
            dividersColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(72.dp)
        )

        NumberPicker(
            value = incrementValue,
            range = incrementValues,
            onValueChange = updateIncrementValue,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.outline,
            ),
            dividersColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.width(72.dp)
        )

        Text(text = "Factor".uppercase(), fontSize = 10.sp)
    }
}

@Composable
inline fun <reified T : Enum<T>> EnumPicker(
    selectedValue: T,
    noinline onSelectValue: (enumValue: T) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        ListItemPicker(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .fillMaxWidth(),
            value = selectedValue,
            onValueChange = onSelectValue,
            list = enumValues<T>().toList(),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            ),
        )
    }
}

@Composable
fun StringField(
    onValueChange: (String) -> Unit,
) {
    var value by remember { mutableStateOf("") }

    val parseValue: (String) -> Unit = {
        value = it
        onValueChange(it)
    }

    ValueField(
        value = value,
        label = "Text",
        keyboardType = KeyboardType.Decimal,
        onValueChange = onValueChange,
    )
}

@Composable
fun FloatField(
    onValueChange: (Float?) -> Unit,
) {
    var value by remember { mutableStateOf("") }

    val parseValue: (String) -> Unit = {
        value = it
        onValueChange(it.toFloatOrNull())
    }

    ValueField(
        value = value,
        label = "Decimal",
        keyboardType = KeyboardType.Decimal,
        onValueChange = parseValue
    )
}

@Composable
fun IntegerField(
    onValueChange: (Int?) -> Unit,
) {
    var value by remember { mutableStateOf("") }

    val parseValue: (String) -> Unit = {
        value = it
        onValueChange(it.toIntOrNull())
    }

    ValueField(
        value = value,
        label = "Integer",
        keyboardType = KeyboardType.Number,
        onValueChange = parseValue
    )
}

@Composable
fun BooleanField(
    onValueChange: (Boolean?) -> Unit,
) {
    var value by remember { mutableStateOf("") }

    val parseValue: (String) -> Unit = {
        val parsedValue = if (it.startsWith('t')) "true"
        else if (it.startsWith('f')) "false"
        else it
        value = parsedValue
        onValueChange(parsedValue.toBooleanStrictOrNull())
    }

    ValueField(
        value = value,
        label = "Boolean",
        keyboardType = KeyboardType.Text,
        onValueChange = parseValue
    )
}

@Composable
fun ValueField(
    value: String,
    label: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
    )
}

// unable to get focusRequester working
@Composable
fun ValueField_Alpha(
    value: String,
    label: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester?,
    imeAction: ImeAction
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                nextFocusRequester?.requestFocus()
            }
        ),
        modifier = Modifier.focusRequester(focusRequester)
    )
}

@Preview(showBackground = true)
@Composable
fun NumberPickerPreview() {
    IntegerPicker(value = 0, onValueChange = { })
}
