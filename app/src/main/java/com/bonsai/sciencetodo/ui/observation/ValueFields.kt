package com.bonsai.sciencetodo.ui.observation

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.bonsai.sciencetodo.ui.common.ValuePickerField

@Composable
fun StringField(
    text: String,
    pickerState: String,
    suggestions: List<String>,
    onValueChange: (String) -> Unit,
) {
    ValuePickerField(
        text = text,
        pickerState = pickerState,
        suggestions = suggestions,
        label = "Text",
        keyboardType = KeyboardType.Text,
        onValueChange = onValueChange
    )
}

@Composable
fun FloatField(
    text: String,
    pickerState: String,
    suggestions: List<String>,
    onValueChange: (String) -> Unit,
) {
    ValuePickerField(
        text = text,
        pickerState = pickerState,
        suggestions = suggestions,
        label = "Decimal",
        keyboardType = KeyboardType.Decimal,
        onValueChange = onValueChange
    )
}

@Composable
fun IntegerField(
    text: String,
    pickerState: String,
    suggestions: List<String>,
    onValueChange: (String) -> Unit
) {
    ValuePickerField(
        text = text,
        pickerState = pickerState,
        suggestions = suggestions,
        label = "Integer",
        keyboardType = KeyboardType.Number,
        onValueChange = onValueChange
    )
}

@Composable
fun BooleanField(
    text: String,
    pickerState: String,
    onValueChange: (String) -> Unit
) {
    ValuePickerField(
        text = text,
        pickerState = pickerState,
        suggestions = listOf("false", pickerNullText, "true"),
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
    ValuePickerField(
        text = text,
        pickerState = pickerState,
        suggestions = suggestions,
        label = "Enumerator",
        keyboardType = KeyboardType.Text,
        onValueChange = onValueChange
    )
}