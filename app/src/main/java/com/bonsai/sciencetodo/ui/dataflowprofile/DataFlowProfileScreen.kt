package com.bonsai.sciencetodo.ui.dataflowprofile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.data.FakeDataFlowDao
import com.bonsai.sciencetodo.data.FakeVariableDao
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.AppViewModelProvider
import com.bonsai.sciencetodo.ui.ScienceToDoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataFlowProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: DataFlowProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            ScienceToDoTopAppBar(
                title = "${uiState.dataFlow.name} Data",
                navigateUp = { navController?.navigateUp() }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxWidth(),
        ) {
            DataFlowDetails(uiState)
            VariableList(uiState)
            AddVariableControl(uiState = uiState, viewModel = viewModel)
        }
    }
}

@Composable
fun DataFlowDetails(uiState: DataFlowProfileUiState, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .fillMaxWidth(),
        ) {
            Text(
                text = uiState.dataFlow?.name ?: "404",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VariableList(uiState: DataFlowProfileUiState, modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
        modifier = modifier
    ) {
        items(uiState.variables, key = { it.id }) {
            VariableCard(variable = it)
        }
    }
}

@Composable
fun VariableCard(variable: Variable, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(text = variable.name)
        }
    }
}

@Composable
fun AddVariableControl(
    uiState: DataFlowProfileUiState,
    viewModel: DataFlowProfileViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        TextField(
            value = uiState.newVariableName,
            onValueChange = viewModel::updateVariableName,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .weight(1f)
                .padding(end = dimensionResource(R.dimen.gap_medium)),
            label = {
                Text(text = "add variable")
            }
        )
        Button(onClick = viewModel::addVariable) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add variable"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDataFlowProfileScreen() {
    val savedStateHandle = SavedStateHandle(
        mapOf(
            AppScreens.DataFlowProfile.dataFlowIdArg to 1
        )
    )

    DataFlowProfileScreen(
        viewModel = DataFlowProfileViewModel(
            dataFlowDao = FakeDataFlowDao(),
            variableDao = FakeVariableDao(),
            savedStateHandle = savedStateHandle
        )
    )
}
