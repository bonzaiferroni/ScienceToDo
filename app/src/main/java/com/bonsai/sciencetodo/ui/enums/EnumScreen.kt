package com.bonsai.sciencetodo.ui.enums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.bonsai.sciencetodo.ui.AppVmProvider
import com.bonsai.sciencetodo.ui.StdTopAppBar
import com.bonsai.sciencetodo.ui.common.StdCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnumScreen(
    navController: NavController? = null,
    viewModel: EnumVm = viewModel(factory = AppVmProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            StdTopAppBar(title = "Enumerations")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = viewModel::openCreateDialog) {
                Icon(Icons.Default.Add, contentDescription = "Create Enum")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            EnumList(uiState.enumerations)
        }
    }
}

@Composable
fun EnumList(
    enumerations: List<Enumeration>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium)),
    ) {
        items(enumerations) {enumeration ->
            StdCard {
                Text(
                    text = enumeration.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }
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