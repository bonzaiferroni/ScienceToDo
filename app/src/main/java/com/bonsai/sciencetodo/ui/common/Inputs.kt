package com.bonsai.sciencetodo.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bonsai.sciencetodo.R
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun IntegerPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var possibleValues: Iterable<Int> by remember {mutableStateOf((-10..10).map { it * 10})}
    val incrementValues = listOf(1, 10, 100, 1000)
    var incrementValue by remember {mutableStateOf(incrementValues[1])}

    val updateIncrementValue: (Int) -> Unit = { newIncrementValue ->
        incrementValue = newIncrementValue
        possibleValues = (-10..10).map { it * incrementValue + value}
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(text = "Value".uppercase(), fontSize = 10.sp)

        NumberPicker (
            value = value,
            range = possibleValues,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.width(72.dp)
        )

        NumberPicker (
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

@Preview(showBackground = true)
@Composable
fun NumberPickerPreview() {
    IntegerPicker(value = 0, onValueChange = { })
}
