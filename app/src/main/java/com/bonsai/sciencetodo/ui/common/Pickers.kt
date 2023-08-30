package com.bonsai.sciencetodo.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.bonsai.sciencetodo.R
import com.chargemap.compose.numberpicker.ListItemPicker
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun ValuePickerField(
    text: String,
    pickerState: String,
    suggestions: List<String>,
    label: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_large))
    ) {
        ValueField(
            value = text,
            label = label,
            keyboardType = keyboardType,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f)
        )

        ListItemPicker(
            label = { it },
            value = pickerState,
            onValueChange = onValueChange,
            list = suggestions,
            textStyle = TextStyle.Default.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            dividersColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TimePicker(
    time: LocalTime,
    onValueChange: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hourString = time.format(DateTimeFormatter.ofPattern("hh"))
    val minuteString = time.format(DateTimeFormatter.ofPattern("mm")).padStart(2, '0')
    val amString = time.format(DateTimeFormatter.ofPattern("a"))
    val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        modifier = modifier,
    ) {
        // hour
        ListItemPicker(
            label = { it },
            value = hourString,
            onValueChange = {
                val newTime = LocalTime.parse("$it:$minuteString $amString", formatter)
                onValueChange(newTime)
            },
            list = hourList,
            textStyle = TextStyle.Default.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            dividersColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )

        // minute
        ListItemPicker(
            label = { it },
            value = minuteString,
            onValueChange = {
                val newTime = LocalTime.parse("$hourString:$it $amString", formatter)
                onValueChange(newTime)
            },
            list = minuteList,
            textStyle = TextStyle.Default.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            dividersColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )

        // am/pm
        ListItemPicker(
            label = { it },
            value = amString,
            onValueChange = {
                val newTime = LocalTime.parse("$hourString:$minuteString $it", formatter)
                onValueChange(newTime)
            },
            list = amList,
            textStyle = TextStyle.Default.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            dividersColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
    }
}

private val hourList = (1..12).map { it.toString() }
private val minuteList = (0..12).map { (it * 5).toString().padStart(2, '0') }
private val amList = listOf("AM", "PM")

@Preview(showBackground = true)
@Composable
fun PreviewTimePicker() {
    TimePicker(LocalTime.of(12, 5), { })
}