package com.bonsai.sciencetodo.ui.dataset

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.data.EnumRepository
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.data.fake.FakeDatasetDao
import com.bonsai.sciencetodo.data.fake.FakeVariableDao
import com.bonsai.sciencetodo.model.Enumeration
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.AppVmProvider
import com.bonsai.sciencetodo.ui.common.AutoCompleteTextField
import com.bonsai.sciencetodo.ui.common.EnumDropdown
import com.bonsai.sciencetodo.ui.common.RowTextButton
import com.bonsai.sciencetodo.ui.common.StdDialog
import com.bonsai.sciencetodo.ui.common.StdIconButton
import com.bonsai.sciencetodo.ui.common.StdRowCard
import com.bonsai.sciencetodo.ui.common.StdTopAppBar
import com.bonsai.sciencetodo.ui.datavalues.ObservationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatasetScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: DatasetVm = viewModel(factory = AppVmProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val onOpenDataView: (datasetId: Int) -> Unit = { datasetId ->
        val route = AppScreens.DataView.getRoute(datasetId)
        navController?.navigate(route)
    }
    val newVariableFunctions = viewModel.NewVariableFunctions()

    ObservationDialog(
        viewModel::saveDataDialog,
        viewModel::cancelDataDialog,
        uiState.newValueBoxes
    )

    Scaffold(
        topBar = {
            StdTopAppBar(
                title = "${uiState.dataset.name} Data",
                navigateUp = { navController?.navigateUp() }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxWidth(),
        ) {
            DatasetDetails(uiState.dataset.name, uiState.observationCount)
            DataButtons(
                viewModel::openDataDialog,
                { onOpenDataView(uiState.dataset.id) },
                newVariableFunctions::start
            )
            VariableList(uiState.variables, viewModel::removeVariable)
            AddVariableControl(
                uiState.newVariable,
                newVariableFunctions,
                enumerations = uiState.enumerations,
            )
        }
    }
}

@Composable
fun DatasetDetails(datasetName: String, observationCount: Int, modifier: Modifier = Modifier) {
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
                text = datasetName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Observations: $observationCount"
            )
        }
    }

}

@Composable
fun VariableList(
    variables: List<Variable>,
    onRemoveVariable: (variable: Variable) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
        modifier = modifier
    ) {
        items(variables, key = { it.id }) { variable ->
            VariableCard(variable, onRemoveVariable)
        }
    }
}

@Composable
fun VariableCard(
    variable: Variable,
    onRemoveVariable: (variable: Variable) -> Unit,
    modifier: Modifier = Modifier
) {
    StdRowCard(modifier = modifier) {
        Text(text = variable.name)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = variable.type.name,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )
        StdIconButton(Icons.Filled.Delete, { onRemoveVariable(variable) })
    }
}

@Composable
fun AddVariableControl(
    newVariable: NewVariable?,
    newVariableFunctions: DatasetVm.NewVariableFunctions,
    enumerations: List<Enumeration>
) {
    if (newVariable == null) return

    StdDialog(
        showDialog = true,
        onDismiss = newVariableFunctions::clear,
        onAccept = newVariableFunctions::save,
        headerText = "add variable",
        enableAccept = newVariable.isValid
    ) {
        TextField(
            value = newVariable.name,
            onValueChange = newVariableFunctions::updateName,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "add variable") }
        )
        EnumDropdown<VariableType>(
            selectedValue = newVariable.variableType,
            onSelectValue = newVariableFunctions::updateType,
        )
        if (newVariable.variableType == VariableType.Enum) {
            val enumerationNames = enumerations.map { it.name }
            AutoCompleteTextField(suggestions = enumerationNames, onValueSelected = { })
        }
    }
}

@Composable
fun DataButtons(
    onAddData: () -> Unit,
    onViewData: () -> Unit,
    onAddVariable: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
        RowTextButton(text = "+ data", onClick = onAddData)
        RowTextButton(text = "+ variable", onClick = onAddVariable)
        RowTextButton(text = "view data", onClick = onViewData)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDatasetScreen() {
    val savedStateHandle = SavedStateHandle(mapOf(AppScreens.datasetIdArg to 1))

    DatasetScreen(
        viewModel = DatasetVm(
            datasetDao = FakeDatasetDao(),
            variableDao = FakeVariableDao(),
            savedStateHandle = savedStateHandle,
            observationRepository = ObservationRepository.getFake(),
            enumRepository = EnumRepository.getFake(),
        )
    )
}
