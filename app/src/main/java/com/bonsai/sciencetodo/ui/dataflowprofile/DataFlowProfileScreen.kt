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
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.data.FakeDataFlowDao
import com.bonsai.sciencetodo.data.FakeIntValueDao
import com.bonsai.sciencetodo.data.FakeObservationDao
import com.bonsai.sciencetodo.data.FakeStringValueDao
import com.bonsai.sciencetodo.data.FakeVariableDao
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.model.VariableType
import com.bonsai.sciencetodo.model.Variable
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.AppViewModelProvider
import com.bonsai.sciencetodo.ui.ScienceToDoTopAppBar
import com.bonsai.sciencetodo.ui.datavalues.DataDialog
import com.bonsai.sciencetodo.ui.datavalues.NewDataValue

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
            DataFlowDetails(uiState.dataFlow.name, uiState.observationCount)
            AddDataControl(
                viewModel::openDataDialog,
                viewModel::saveDataDialog,
                viewModel::cancelDataDialog,
                uiState.newDataValues
            )
            VariableList(uiState.variables, viewModel::removeVariable)
            AddVariableControl(
                newVariableName = uiState.newVariableName,
                newVariableType = uiState.newVariableType,
                updateVariableName = viewModel::updateVariableName,
                updateVariableType = viewModel::updateVariableType,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVariableControl(
    newVariableName: String,
    newVariableType: VariableType,
    updateVariableName: (variableName: String) -> Unit,
    updateVariableType: (variableType: VariableType) -> Unit,
    addVariable: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        TextField(
            value = newVariableName,
            onValueChange = updateVariableName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.gap_medium)),
            label = {
                Text(text = "add variable")
            }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .weight(1f)
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
            Button(onClick = addVariable) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add variable"
                )
            }
        }

    }
}

@Composable
fun AddDataControl(
    onOpenDialog: () -> Unit,
    onSaveDialog: () -> Unit,
    onCancelDialog: () -> Unit,
    newDataValues: List<NewDataValue>?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Button(
            onClick = onOpenDialog,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "add data")
        }
        DataDialog(
            onSaveDialog,
            onCancelDialog,
            newDataValues
        )
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

    val observationRepository = ObservationRepository(
        FakeObservationDao(),
        FakeStringValueDao(),
        FakeIntValueDao()
    )

    DataFlowProfileScreen(
        viewModel = DataFlowProfileViewModel(
            dataFlowDao = FakeDataFlowDao(),
            variableDao = FakeVariableDao(),
            savedStateHandle = savedStateHandle,
            observationRepository = observationRepository
        )
    )
}
