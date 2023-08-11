package com.bonsai.sciencetodo.ui.dataflowprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bonsai.sciencetodo.data.FakeDataFlowDao
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.AppViewModelProvider
import com.bonsai.sciencetodo.ui.ScienceToDoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataFlowProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: DataFlowProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            ScienceToDoTopAppBar(
                title = uiState.dataFlow?.name ?: "DataFlow Profile",
                navigateUp = navController::navigateUp
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .background(Color.Gray)
                .fillMaxWidth(),
        ) {
            DataFlowDetails(uiState)
        }
    }
}

@Composable
fun DataFlowDetails(uiState: DataFlowProfileUiState) {
    Column(
        modifier = Modifier.padding()
    ) {
        Text(
            text = uiState.dataFlow?.name ?: "404",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDataFlowProfileScreen() {
    val savedStateHandle = SavedStateHandle(mapOf(
        AppScreens.DataFlowProfile.dataFlowIdArg to 1
    ))

    DataFlowProfileScreen(
        viewModel = DataFlowProfileViewModel(
            dataFlowDao = FakeDataFlowDao(),
            savedStateHandle = savedStateHandle
        )
    )
}
