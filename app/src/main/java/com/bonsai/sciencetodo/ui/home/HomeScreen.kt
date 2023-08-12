package com.bonsai.sciencetodo.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.data.FakeDataFlowDao
import com.bonsai.sciencetodo.data.FakeVariableDao
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.AppViewModelProvider
import com.bonsai.sciencetodo.ui.ScienceToDoTopAppBar
import com.bonsai.sciencetodo.ui.datavalues.DataDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val onClickCard: (DataFlow) -> Unit = { dataFlow ->
        val route = AppScreens.DataFlowProfile.getRoute(dataFlow)
        navController?.navigate(route)
    }

    if (uiState.showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.hideDialog() },
            title = { Text("Add a new DataFlow") },
            text = {
                OutlinedTextField(
                    value = uiState.dataFlowName,
                    onValueChange = { viewModel.onNameChange(it) },
                    label = { Text("Name") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.addDataFlow() }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.hideDialog() }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            ScienceToDoTopAppBar(title = "Science ToDo")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showDialog() }) {
                Icon(Icons.Default.Add, contentDescription = "Add DataFlow")
            }
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(uiState.dataFlows) { dataFlow ->
                DataFlowCard(dataFlow, onClickCard, viewModel::openDataDialog)
            }
        }
    }

    DataDialog(
        viewModel::saveDataDialog,
        viewModel::cancelDataDialog,
        uiState.newDataValues
    )
}

@Composable
fun DataFlowCard(
    dataFlow: DataFlow,
    onOpenFlow: (DataFlow) -> Unit,
    onOpenDataDialog: (dataFlowId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = dataFlow.name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onOpenDataDialog(dataFlow.id) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add data")
            }
            IconButton(onClick = { onOpenFlow(dataFlow) }) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "view flow")
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        viewModel = HomeViewModel(
            dataFlowDao = FakeDataFlowDao(),
            variableDao = FakeVariableDao(),
            ObservationRepository.getFake()
        )
    )
}
