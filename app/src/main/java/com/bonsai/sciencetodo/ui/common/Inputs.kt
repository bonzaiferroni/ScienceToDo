package com.bonsai.sciencetodo.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
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
    text: String,
    onValueChange: (String) -> Unit,
) {
    ValueField(
        value = text,
        label = "Text",
        keyboardType = KeyboardType.Decimal,
        onValueChange = onValueChange,
    )
}

@Composable
fun FloatField(
    text: String,
    onValueChange: (String) -> Unit,
) {
    ValueField(
        value = text,
        label = "Decimal",
        keyboardType = KeyboardType.Decimal,
        onValueChange = onValueChange,
    )
}

@Composable
fun IntegerField(
    text: String,
    onValueChange: (String) -> Unit
) {
    ValueField(
        value = text,
        label = "Integer",
        keyboardType = KeyboardType.Number,
        onValueChange = onValueChange
    )
}

@Composable
fun BooleanField(
    text: String,
    onValueChange: (String) -> Unit
) {
    ValueField(
        value = text,
        label = "Boolean",
        keyboardType = KeyboardType.Text,
        onValueChange = onValueChange
    )
}

@Composable
fun EnumField(
    text: String,
    pickerState: String,
    suggestions: List<String>,
    onValueChange: (String) -> Unit
) {
    ValueField(
        value = text,
        label = "Enumerator",
        keyboardType = KeyboardType.Text,
        onValueChange = onValueChange
    )

    ListItemPicker(
        label = { it },
        value = pickerState,
        onValueChange = onValueChange,
        list = suggestions,
        textStyle = TextStyle.Default.copy(
            color = MaterialTheme.colorScheme.onBackground
        ),
        dividersColor = MaterialTheme.colorScheme.primary
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

@Composable
fun AutoCompleteTextField(
    text: String,
    suggestions: List<String>,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Enter text",
    imeAction: ImeAction = ImeAction.Done
) {
    var isDropdownExpanded by remember { mutableStateOf(true) }

    // Box is used to overlap the dropdown on the UI
    Box {
        TextField(
            value = text,
            onValueChange = {
                isDropdownExpanded = true
                onValueChanged(it)
            },
            label = { Text(label) },
            modifier = modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = imeAction
            )
        )

        // Display suggestions
        DropdownMenu(
            expanded = isDropdownExpanded && suggestions.isNotEmpty(),
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = { isDropdownExpanded = false }
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(
                    text = { Text(suggestion) },
                    onClick = {
                        isDropdownExpanded = false
                        onValueChanged(suggestion)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NumberPickerPreview() {
    IntegerPicker(value = 0, onValueChange = { })
}
