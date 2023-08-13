package com.bonsai.sciencetodo.ui.dataview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.bonsai.sciencetodo.data.DataRepository
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.AppVmProvider
import com.bonsai.sciencetodo.ui.ScienceToDoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataViewScreen(
    navController: NavController? = null,
    viewModel: DataViewVm = viewModel(factory = AppVmProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val tableContent = uiState.dataTableContent

    Scaffold(
        topBar = {
            ScienceToDoTopAppBar(title = "Data: ${uiState.dataFlow.name}")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (tableContent != null) {
                DataTable(
                    tableContent = tableContent,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDataViewScreen() {
    val savedStateHandle = SavedStateHandle(mapOf(AppScreens.dataFlowIdArg to 1))
    DataViewScreen(
        viewModel = DataViewVm(
            savedStateHandle = savedStateHandle,
            dataRepository = DataRepository.getFake()
        )
    )
}