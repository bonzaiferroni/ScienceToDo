package com.bonsai.sciencetodo.ui.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.data.IntervalUnit
import com.bonsai.sciencetodo.ui.common.RowIconButton
import com.bonsai.sciencetodo.ui.common.StdTopAppBar
import com.bonsai.sciencetodo.ui.common.TimePicker
import com.bonsai.sciencetodo.ui.common.ValuePickerField
import com.chargemap.compose.numberpicker.ListItemPicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRoutineScreen(
    navController: NavController?,
    viewModel: NewRoutineVm = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            StdTopAppBar(
                title = "New Routine",
                navigateUp = { navController?.navigateUp() },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                ValuePickerField(
                    text = uiState.datasetText,
                    pickerState = uiState.datasetPickerState,
                    suggestions = uiState.datasetSuggestions,
                    label = "Dataset",
                    keyboardType = KeyboardType.Text,
                    onValueChange = viewModel::setDatasetText
                )
                TimePicker(
                    time = uiState.baseTime,
                    onValueChange = viewModel::setBaseTime
                )
                IntervalPicker(
                    intervalPickerState = uiState.intervalPickerState,
                    onIntervalChange = viewModel::setInterval,
                    unitPickerState = uiState.unitPickerState,
                    onUnitChange = viewModel::setIntervalUnit,
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            ) {
                RowIconButton(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close dialog",
                    onClick = { navController?.navigateUp() }
                )
                RowIconButton(
                    imageVector = Icons.Default.Check,
                    contentDescription = "add variable",
                    onClick = { viewModel.saveObservation { navController?.navigateUp() } },
                    enabled = uiState.enableAccept,
                )
            }
        }
    }
}

@Composable
fun IntervalPicker(
    intervalPickerState: Int,
    onIntervalChange: (Int) -> Unit,
    unitPickerState: IntervalUnit,
    onUnitChange: (IntervalUnit) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
        modifier = modifier
    ) {
        ListItemPicker(
            label = { it.toString() },
            value = intervalPickerState,
            onValueChange = onIntervalChange,
            list = (1..60).toList(),
            textStyle = TextStyle.Default.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            dividersColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
        ListItemPicker(
            label = { it.toString() },
            value = unitPickerState,
            onValueChange = onUnitChange,
            list = listOf(
                IntervalUnit.Minute, IntervalUnit.Hour, IntervalUnit.Day, IntervalUnit.Week
            ),
            textStyle = TextStyle.Default.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            dividersColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
    }
}