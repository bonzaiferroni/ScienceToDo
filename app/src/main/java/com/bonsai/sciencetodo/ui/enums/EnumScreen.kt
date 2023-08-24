package com.bonsai.sciencetodo.ui.enums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
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
import com.bonsai.sciencetodo.ui.common.EditFunctions
import com.bonsai.sciencetodo.ui.common.MoreMenu
import com.bonsai.sciencetodo.ui.common.MoreMenuItem
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

    NewEnumerationDialog(
        uiState.newEnumeration,
        viewModel.newEnumerationFunctions
    )

    CreateEnumeratorDialog(
        uiState.newEnumerator,
        viewModel.newEnumeratorFunctions
    )

    Scaffold(
        topBar = {
            StdTopAppBar(title = "Enumerations")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.newEnumerationFunctions.start(Unit) }) {
                Icon(Icons.Default.Add, contentDescription = "Create Enum")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            EnumList(
                enumerations = uiState.enumerations,
                enumeratorMap = uiState.enumeratorMap,
                enumerationNameEdit = uiState.enumerationNameEdit,
                enumerationNameEditFunctions = viewModel.enumerationNameEditFunctions,
                onDeleteEnumeration = viewModel::deleteEnumeration,
                enumeratorNameEdit = uiState.enumeratorNameEdit,
                enumeratorNameEditFunctions = viewModel.enumeratorNameEditFunctions,
                onMoveEnumerator = viewModel::moveEnumerator,
                onDeleteEnumerator = viewModel::deleteEnumerator,
                onAddEnumerator = viewModel.newEnumeratorFunctions.start
            )
        }
    }
}

@Composable
fun NewEnumerationDialog(
    newEnumeration: NewEnumeration?,
    editFunctions: EditFunctions<Unit>
) {
    if (newEnumeration == null) return

    StdDialog(
        showDialog = true,
        onDismiss = editFunctions.clear,
        onAccept = editFunctions.save,
        enableAccept = newEnumeration.isValid
    ) {
        TextField(
            value = newEnumeration.name,
            onValueChange = editFunctions.update,
            label = { Text(text = "new enumeration")},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CreateEnumeratorDialog(
    newEnumerator: NewEnumerator?,
    editFunctions: EditFunctions<Int>
) {
    if (newEnumerator == null) return

    StdDialog(
        showDialog = true,
        onDismiss = editFunctions.clear,
        onAccept = editFunctions.save,
        enableAccept = newEnumerator.isValid,
    ) {
        TextField(
            value = newEnumerator.name,
            onValueChange = editFunctions.update,
            label = { Text(text = "new enum member")},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun EnumList(
    enumerations: List<Enumeration>,
    enumeratorMap: Map<Int, List<Enumerator>>,
    enumerationNameEdit: EnumerationNameEdit?,
    enumerationNameEditFunctions: EditFunctions<Enumeration>,
    onDeleteEnumeration: (Enumeration) -> Unit,
    enumeratorNameEdit: EnumeratorNameEdit?,
    enumeratorNameEditFunctions: EditFunctions<Enumerator>,
    onMoveEnumerator: (enumerator: Enumerator, newIndex: Int) -> Unit,
    onDeleteEnumerator: (enumerator: Enumerator) -> Unit,
    onAddEnumerator: (Int) -> Unit,
    modifier: Modifier = Modifier,
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
                EnumerationCard(
                    enumeration = enumeration,
                    enumerationNameEdit = enumerationNameEdit,
                    editFunctions = enumerationNameEditFunctions,
                    onAddEnumerator = onAddEnumerator,
                    onDeleteEnumeration = onDeleteEnumeration
                )
                EnumeratorList(
                    enumeration = enumeration,
                    enumeratorMap = enumeratorMap,
                    enumeratorNameEdit = enumeratorNameEdit,
                    editFunctions = enumeratorNameEditFunctions,
                    onMoveEnumerator = onMoveEnumerator,
                    onDeleteEnumerator = onDeleteEnumerator,
                )
            }
        }
    }
}

@Composable
fun EnumerationCard(
    enumeration: Enumeration,
    enumerationNameEdit: EnumerationNameEdit?,
    editFunctions: EditFunctions<Enumeration>,
    onAddEnumerator: (Int) -> Unit,
    onDeleteEnumeration: (Enumeration) -> Unit,
) {
    StdRowCard {
        if (enumerationNameEdit != null && enumerationNameEdit.id == enumeration.id) {
            TextField(
                value = enumerationNameEdit.name,
                onValueChange = editFunctions.update,
            )
            StdIconButton(Icons.Filled.Clear, editFunctions.clear)
            StdIconButton(Icons.Filled.Done, editFunctions.save)

        } else {
            Text(
                text = enumeration.name,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        MoreMenu(
            listOf(
                MoreMenuItem("edit name") { editFunctions.start(enumeration) },
                MoreMenuItem("add member") { onAddEnumerator(enumeration.id) },
                MoreMenuItem("delete") { onDeleteEnumeration(enumeration) }
            )
        )
    }
}

@Composable
fun EnumeratorList(
    enumeration: Enumeration,
    enumeratorMap: Map<Int, List<Enumerator>>,
    enumeratorNameEdit: EnumeratorNameEdit?,
    editFunctions: EditFunctions<Enumerator>,
    onMoveEnumerator: (enumerator: Enumerator, newIndex: Int) -> Unit,
    onDeleteEnumerator: (enumerator: Enumerator) -> Unit,
) {
    val enumeratorList = enumeratorMap[enumeration.id] ?: return

    enumeratorList.indices.forEach { index ->
        val enumerator = enumeratorList[index]
        StdRowCard(
            modifier = Modifier.padding(start = dimensionResource(R.dimen.indent)),
        ) {
            if (enumeratorNameEdit != null && enumeratorNameEdit.id == enumerator.id) {
                TextField(
                    value = enumeratorNameEdit.name,
                    onValueChange = editFunctions.update,
                )
                Spacer(modifier = Modifier.weight(1f))
                StdIconButton(imageVector = Icons.Filled.Clear, onClick = editFunctions.clear)
                StdIconButton(imageVector = Icons.Filled.Done, onClick = editFunctions.save)
            } else {
                Text(
                    text = enumerator.name
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            val menuItems = mutableListOf(
                MoreMenuItem("edit name") { editFunctions.start(enumerator) },
                MoreMenuItem("delete") { onDeleteEnumerator(enumerator) },
            )
            if (index > 0) {
                menuItems.add(MoreMenuItem("move up") { onMoveEnumerator(enumerator, index - 1) })
            }
            if (index < enumeratorList.size - 1) {
                menuItems.add(MoreMenuItem("move down") { onMoveEnumerator(enumerator, index + 1) })
            }

            MoreMenu(
                moreMenuItems = menuItems
            )
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