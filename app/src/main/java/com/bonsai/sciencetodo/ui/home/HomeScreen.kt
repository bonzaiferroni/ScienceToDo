package com.bonsai.sciencetodo.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.AppViewModelProvider
import com.bonsai.sciencetodo.ui.ScienceToDoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val onClickCard: (DataFlow) -> Unit = { dataFlow ->
        navController.navigate(AppScreens.DataFlowProfile.getRoute(dataFlow))
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
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(uiState.dataFlows) { dataFlow ->
                DataFlowCard(dataFlow, onClickCard)
            }
        }
    }
}

@Composable
fun DataFlowCard(
    dataFlow: DataFlow,
    onClickCard: (DataFlow) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = dataFlow.name,
                modifier = Modifier.clickable { onClickCard(dataFlow) }
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
}
