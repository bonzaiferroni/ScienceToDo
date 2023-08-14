package com.bonsai.sciencetodo.ui.dataflowprofile

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.fakedata.FakeDataFlowDao
import com.bonsai.sciencetodo.fakedata.FakeVariableDao
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.model.VariableType
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.AppVmProvider
import com.bonsai.sciencetodo.ui.ScienceToDoTopAppBar
import com.bonsai.sciencetodo.ui.datavalues.ObservationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: DataProfileVm = viewModel(factory = AppVmProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val onOpenDataView: (dataFlowId: Int) -> Unit = { dataFlowId ->
        val route = AppScreens.DataView.getRoute(dataFlowId)
        navController?.navigate(route)
    }

    ObservationDialog(
        viewModel::saveDataDialog,
        viewModel::cancelDataDialog,
        uiState.newDataValues
    )

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
            DataFlowDetails(uiState.dataFlow.name, uiState.observationCount)
            DataButtons(
                viewModel::openDataDialog,
                { onOpenDataView(uiState.dataFlow.id) },
                viewModel::openAddVariableDialog
            )
            VariableList(uiState.variables, viewModel::removeVariable)
            AddVariableControl(
                showDialog = uiState.showAddVariableDialog,
                newVariableName = uiState.newVariableName,
                newVariableType = uiState.newVariableType,
                updateVariableName = viewModel::updateVariableName,
                updateVariableType = viewModel::updateVariableType,
                onCloseDialog = viewModel::cancelAddVariableDialog,
                addVariable = viewModel::addVariable
            )
        }
    }
}

@Composable
fun DataFlowDetails(dataFlowName: String, observationCount: Int, modifier: Modifier = Modifier) {
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
                text = dataFlowName,
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
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(text = variable.name)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = variable.type.name,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
            IconButton(onClick = { onRemoveVariable(variable) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "remove variable"
                )
            }
        }
    }
}

@Composable
fun AddVariableControl(
    showDialog: Boolean,
    newVariableName: String,
    newVariableType: VariableType,
    updateVariableType: (variableType: VariableType) -> Unit,
    updateVariableName: (variableName: String) -> Unit,
    onCloseDialog: () -> Unit,
    addVariable: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!showDialog) return

    Dialog(
        onDismissRequest = onCloseDialog,
        properties = DialogProperties(
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                verticalArrangement = Arrangement
                    .spacedBy(dimensionResource(R.dimen.gap_medium)),
                modifier = modifier
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = "Add Variable".uppercase(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = newVariableName,
                    onValueChange = updateVariableName,
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "add variable")
                    }
                )
                VariableTypeDropdown(
                    newVariableType = newVariableType,
                    updateVariableType = updateVariableType
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement
                        .spacedBy(dimensionResource(R.dimen.gap_medium))
                ) {
                    Button(
                        onClick = addVariable,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close dialog"
                        )
                    }
                    Button(
                        onClick = addVariable,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "add variable"
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariableTypeDropdown(
    newVariableType: VariableType,
    updateVariableType: (variableType: VariableType) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = newVariableType.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            VariableType.values().forEach { variableType ->
                DropdownMenuItem(
                    text = {
                        Text(text = variableType.name)
                    }, onClick = {
                        updateVariableType(variableType)
                        expanded = false
                    })
            }
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
        val buttonModifier = Modifier.weight(1f)
        Button(onClick = onAddData, modifier = buttonModifier) { Text(text = "+ data") }
        Button(onClick = onAddVariable, modifier = buttonModifier) { Text(text = "+ variable") }
        Button(onClick = onViewData, modifier = buttonModifier) { Text(text = "view data") }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDataFlowProfileScreen() {
    val savedStateHandle = SavedStateHandle(mapOf(AppScreens.dataFlowIdArg to 1))

    DataProfileScreen(
        viewModel = DataProfileVm(
            dataFlowDao = FakeDataFlowDao(),
            variableDao = FakeVariableDao(),
            savedStateHandle = savedStateHandle,
            observationRepository = ObservationRepository.getFake()
        )
    )
}
