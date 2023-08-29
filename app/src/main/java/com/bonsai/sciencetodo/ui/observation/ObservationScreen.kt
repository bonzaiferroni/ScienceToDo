package com.bonsai.sciencetodo.ui.observation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.ui.common.BooleanField
import com.bonsai.sciencetodo.ui.common.EnumField
import com.bonsai.sciencetodo.ui.common.FloatField
import com.bonsai.sciencetodo.ui.common.IntegerField
import com.bonsai.sciencetodo.ui.common.RowIconButton
import com.bonsai.sciencetodo.ui.common.StdTopAppBar
import com.bonsai.sciencetodo.ui.common.StringField
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObservationScreen(
    navController: NavController? = null,
    viewModel: ObservationVm = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val (dataset, newValueBoxes, enableAccept) = uiState

    Scaffold(
        topBar = {
            StdTopAppBar(
                title = "Observation: ${dataset.name}",
                navigateUp = { navController?.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .weight(1f)
            ) {
                items(newValueBoxes) { newValueBox ->
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

                            // header
                            Text(
                                text = variable.name.uppercase(Locale.getDefault()),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.labelSmall
                            )

                            // input field
                            when (newValueBox) {
                                is NewInteger -> IntegerField(
                                    text = newValueBox.text,
                                    pickerState = newValueBox.pickerState,
                                    suggestions = newValueBox.suggestions,
                                ) {
                                    viewModel.setValue(newValueBox, it)
                                }

                                is NewString -> StringField(
                                    text = newValueBox.text,
                                    pickerState = newValueBox.pickerState,
                                    suggestions = newValueBox.suggestions,
                                ) {
                                    viewModel.setValue(newValueBox, it)
                                }

                                is NewFloat -> FloatField(
                                    text = newValueBox.text,
                                    pickerState = newValueBox.pickerState,
                                    suggestions = newValueBox.suggestions,
                                ) {
                                    viewModel.setValue(newValueBox, it)
                                }

                                is NewBoolean -> BooleanField(
                                    text = newValueBox.text,
                                    pickerState = newValueBox.pickerState
                                ) {
                                    viewModel.setValue(newValueBox, it)
                                }

                                is NewEnum -> EnumField(
                                    text = newValueBox.text,
                                    pickerState = newValueBox.pickerState,
                                    suggestions = newValueBox.suggestions
                                ) {
                                    viewModel.setValue(newValueBox, it)
                                }
                            }
                        }
                    }
                }
            }

            // accept and cancel
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
                    enabled = enableAccept,
                )
            }
        }
    }
}