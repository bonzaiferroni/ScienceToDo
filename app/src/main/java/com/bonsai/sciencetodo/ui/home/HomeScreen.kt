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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.data.fake.FakeDatasetDao
import com.bonsai.sciencetodo.data.fake.FakeVariableDao
import com.bonsai.sciencetodo.model.Dataset
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.common.StdIconButton
import com.bonsai.sciencetodo.ui.common.StdRowCard
import com.bonsai.sciencetodo.ui.common.StdTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController? = null,
    viewModel: HomeVm = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val onClickCard: (datasetId: Int) -> Unit = { datasetId ->
        val route = AppScreens.Dataset.getRoute(datasetId)
        navController?.navigate(route)
    }
    val onOpenDataView: (datasetId: Int) -> Unit = { datasetId ->
        val route = AppScreens.DataView.getRoute(datasetId)
        navController?.navigate(route)
    }

    AddDatasetDialog(
        uiState.showDialog,
        uiState.newDatasetName,
        viewModel::hideDialog,
        viewModel::onNameChange,
        viewModel::addDataset
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
                Icon(Icons.Default.Add, contentDescription = "Add Dataset")
            }
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            DatasetList(
                uiState.datasets,
                onClickCard,
                onOpenDataView,
                viewModel::openDataDialog
            )
        }
    }
}

@Composable
fun DatasetList(
    datasets: List<Dataset>,
    onOpenFlow: (datasetId: Int) -> Unit,
    onOpenDataView: (datasetId: Int) -> Unit,
    onOpenDataDialog: (datasetId: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
        modifier = modifier
            .fillMaxSize()
    ) {
        items(datasets) { dataset ->
            StdRowCard {
                Text(
                    text = dataset.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                StdIconButton(Icons.Filled.Add, { onOpenDataDialog(dataset.id) })
                StdIconButton(Icons.Filled.Search, { onOpenDataView(dataset.id) })
                StdIconButton(Icons.Filled.ArrowForward, { onOpenFlow(dataset.id) })
            }
        }
    }
}

@Composable
fun AddDatasetDialog(
    showDialog: Boolean,
    newDatasetName: String,
    onHideDialog: () -> Unit,
    onChangeName: (name: String) -> Unit,
    onAddDataset: () -> Unit,
) {
    if (showDialog) {

        AlertDialog(
            onDismissRequest = onHideDialog,
            title = { Text("Add a new dataset") },
            text = {
                OutlinedTextField(
                    value = newDatasetName,
                    onValueChange = onChangeName,
                    label = { Text("Name") },
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onAddDataset
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
            datasetDao = FakeDatasetDao(),
            variableDao = FakeVariableDao(),
            ObservationRepository.getFake()
        )
    )
}
