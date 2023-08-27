package com.bonsai.sciencetodo.ui.observation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium))
            ) {

                // value input card
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

                            // header
                            Text(
                                text = variable.name.uppercase(Locale.getDefault()),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.labelSmall
                            )

                            // input field
                            when (newValueBox) {
                                is NewInteger -> IntegerField(newValueBox)
                                is NewString -> StringField(newValueBox)
                                is NewFloat -> FloatField(newValueBox)
                                is NewBoolean -> BooleanField(newValueBox)
                                is NewEnum -> EnumField(newValueBox)
                            }
                        }
                    }
                }

                // accept and cancel
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
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
}