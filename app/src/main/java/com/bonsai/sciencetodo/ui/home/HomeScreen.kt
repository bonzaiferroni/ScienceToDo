package com.bonsai.sciencetodo.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.data.fake.FakeDataFlowDao
import com.bonsai.sciencetodo.data.fake.FakeVariableDao
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.AppVmProvider
import com.bonsai.sciencetodo.ui.common.StdIconButton
import com.bonsai.sciencetodo.ui.common.StdRowCard
import com.bonsai.sciencetodo.ui.common.StdTopAppBar
import com.bonsai.sciencetodo.ui.datavalues.ObservationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: HomeVm = viewModel(factory = AppVmProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val onClickCard: (dataFlowId: Int) -> Unit = { dataFlowId ->
        val route = AppScreens.DataFlowProfile.getRoute(dataFlowId)
        navController?.navigate(route)
    }
    val onOpenDataView: (dataFlowId: Int) -> Unit = { dataFlowId ->
        val route = AppScreens.DataView.getRoute(dataFlowId)
        navController?.navigate(route)
    }

    AddDataFlowDialog(
        uiState.showDialog,
        uiState.newDataFlowName,
        viewModel::hideDialog,
        viewModel::onNameChange,
        viewModel::addDataFlow
    )

    Scaffold(
        topBar = {
            StdTopAppBar(title = "Science ToDo") {
                DropdownMenuItem(
                    text = { Text("Enumerations") },
                    onClick = { navController?.navigate(AppScreens.Enum.name) }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showDialog() }) {
                Icon(Icons.Default.Add, contentDescription = "Add DataFlow")
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            DataFlowList(
                uiState.dataFlows,
                onClickCard,
                onOpenDataView,
                viewModel::openDataDialog
            )
        }
    }

    ObservationDialog(
        viewModel::saveDataDialog,
        viewModel::cancelDataDialog,
        uiState.newValueBoxes
    )
}

@Composable
fun DataFlowList(
    dataFlows: List<DataFlow>,
    onOpenFlow: (dataFlowId: Int) -> Unit,
    onOpenDataView: (dataFlowId: Int) -> Unit,
    onOpenDataDialog: (dataFlowId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
        modifier = modifier
            .fillMaxSize()
    ) {
        items(dataFlows) { dataFlow ->
            StdRowCard {
                Text(
                    text = dataFlow.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                StdIconButton(Icons.Filled.Add, { onOpenDataDialog(dataFlow.id) })
                StdIconButton(Icons.Filled.Search, { onOpenDataView(dataFlow.id) })
                StdIconButton(Icons.Filled.ArrowForward, { onOpenFlow(dataFlow.id) })
            }
        }
    }
}

@Composable
fun AddDataFlowDialog(
    showDialog: Boolean,
    newDataFlowName: String,
    onHideDialog: () -> Unit,
    onChangeName: (name: String) -> Unit,
    onAddDataFlow: () -> Unit,
) {
    if (showDialog) {

        AlertDialog(
            onDismissRequest = onHideDialog,
            title = { Text("Add a new DataFlow") },
            text = {
                OutlinedTextField(
                    value = newDataFlowName,
                    onValueChange = onChangeName,
                    label = { Text("Name") },
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onAddDataFlow
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onHideDialog
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        viewModel = HomeVm(
            dataFlowDao = FakeDataFlowDao(),
            variableDao = FakeVariableDao(),
            ObservationRepository.getFake()
        )
    )
}
