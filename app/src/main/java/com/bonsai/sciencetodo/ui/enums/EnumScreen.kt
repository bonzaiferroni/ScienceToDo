package com.bonsai.sciencetodo.ui.enums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.data.EnumRepository
import com.bonsai.sciencetodo.data.fake.FakeEnumValueDao
import com.bonsai.sciencetodo.data.fake.FakeEnumVarJoinDao
import com.bonsai.sciencetodo.data.fake.FakeEnumerationDao
import com.bonsai.sciencetodo.data.fake.FakeEnumeratorDao
import com.bonsai.sciencetodo.model.Enumeration
import com.bonsai.sciencetodo.model.Enumerator
import com.bonsai.sciencetodo.ui.AppVmProvider
import com.bonsai.sciencetodo.ui.common.StdDialog
import com.bonsai.sciencetodo.ui.common.StdIconButton
import com.bonsai.sciencetodo.ui.common.StdRowCard
import com.bonsai.sciencetodo.ui.common.StdTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnumScreen(
    navController: NavController? = null,
    viewModel: EnumVm = viewModel(factory = AppVmProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    CreateEnumDialog(
        uiState.newEnumName,
        uiState.isValidEnumName,
        viewModel::clearNewEnum,
        viewModel::saveNewEnum,
        viewModel::updateNewEnum,
    )

    Scaffold(
        topBar = {
            StdTopAppBar(title = "Enumerations")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::createNewEnum) {
                Icon(Icons.Default.Add, contentDescription = "Create Enum")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            EnumList(
                enumerations = uiState.enumerations,
                enumeratorMap = uiState.enumeratorMap,
                editingEnumeration = uiState.editingEnumeration,
                editingEnumerator = uiState.editingEnumerator,
                onEditEnumerationName = viewModel::editEnumerationName,
                saveEnumerationName = viewModel::saveEditEnumeration,
                onEditEnumeratorName = viewModel::editEnumeratorName,
                onEditEnumeration = viewModel::editEnumeration,
                onEditEnumerator = viewModel::editEnumerator,
                onMoveEnumerator = viewModel::moveEnumerator,
                onDeleteEnumerator = viewModel::deleteEnumerator,
            )
        }
    }
}

@Composable
fun CreateEnumDialog(
    newEnumName: String?,
    isValidEnumName: Boolean,
    onCancelDialog: () -> Unit,
    onSaveDialog: () -> Unit,
    onEditValue: (String) -> Unit,
) {
    if (newEnumName == null) return;

    StdDialog(
        showDialog = true,
        onDismiss = onCancelDialog,
        onAccept = onSaveDialog,
        enableAccept = isValidEnumName
    ) {
        TextField(
            value = newEnumName,
            onValueChange = onEditValue
        )
    }
}

@Composable
fun EnumList(
    enumerations: List<Enumeration>,
    enumeratorMap: Map<Int, List<Enumerator>>,
    editingEnumeration: Pair<Int, String>?,
    editingEnumerator: Pair<Int, String>?,
    onEditEnumerationName: (String) -> Unit,
    saveEnumerationName: () -> Unit,
    onEditEnumeratorName: (String) -> Unit,
    onEditEnumeration: (enumeration: Enumeration) -> Unit,
    onEditEnumerator: (enumerator: Enumerator) -> Unit,
    onMoveEnumerator: (enumerator: Enumerator, newIndex: Int) -> Unit,
    onDeleteEnumerator: (enumerator: Enumerator) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
    ) {
        items(enumerations) { enumeration ->
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_small)),
            ) {
                StdRowCard {
                    if (editingEnumeration != null && editingEnumeration.first == enumeration.id) {
                        TextField(
                            value = editingEnumeration.second,
                            onValueChange = onEditEnumerationName,
                        )
                    } else {
                        Text(
                            text = enumeration.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if (editingEnumeration != null && editingEnumeration.first == enumeration.id) {
                        StdIconButton(Icons.Filled.Done, saveEnumerationName)
                    } else {
                        StdIconButton(Icons.Filled.Edit, { onEditEnumeration(enumeration) })
                    }
                }
                EnumeratorList(
                    enumeration = enumeration,
                    enumeratorMap = enumeratorMap,
                    editingEnumerator = editingEnumerator,
                    onEditEnumeratorValue = onEditEnumeratorName,
                    onEditEnumerator = onEditEnumerator,
                    onMoveEnumerator = onMoveEnumerator,
                    onDeleteEnumerator = onDeleteEnumerator,
                )
            }
        }
    }
}

@Composable
fun EnumeratorList(
    enumeration: Enumeration,
    enumeratorMap: Map<Int, List<Enumerator>>,
    editingEnumerator: Pair<Int, String>?,
    onEditEnumeratorValue: (String) -> Unit,
    onEditEnumerator: (enumerator: Enumerator) -> Unit,
    onMoveEnumerator: (enumerator: Enumerator, newIndex: Int) -> Unit,
    onDeleteEnumerator: (enumerator: Enumerator) -> Unit,
) {
    val enumeratorList = enumeratorMap[enumeration.id] ?: return

    enumeratorList.indices.forEach { index ->
        val enumerator = enumeratorList[index]
        StdRowCard(
            modifier = Modifier.padding(start = dimensionResource(R.dimen.indent))
        ) {
            if (editingEnumerator != null && editingEnumerator.first == enumerator.id) {
                TextField(
                    value = editingEnumerator.second,
                    onValueChange = onEditEnumeratorValue,
                )
            } else {
                Text(
                    text = enumerator.name
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            StdIconButton(Icons.Filled.Edit, { onEditEnumerator(enumerator) })
            if (index > 0) {
                StdIconButton(
                    Icons.Filled.KeyboardArrowUp,
                    { onMoveEnumerator(enumerator, index - 1) })
            }
            if (index < enumeratorMap.size - 1) {
                StdIconButton(
                    Icons.Filled.KeyboardArrowDown,
                    { onMoveEnumerator(enumerator, index + 1) })
            }
            StdIconButton(Icons.Filled.KeyboardArrowDown, { onDeleteEnumerator(enumerator) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEnumScreen() {
    val enumRepository = EnumRepository(
        FakeEnumerationDao(),
        FakeEnumeratorDao(),
        FakeEnumValueDao(),
        FakeEnumVarJoinDao()
    )
    val enumVm = EnumVm(enumRepository)
    EnumScreen(viewModel = enumVm)
}