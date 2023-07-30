package com.bonsai.sciencetodo.ui.dataflowprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.ui.AppViewModelProvider
import com.bonsai.sciencetodo.ui.ScienceToDoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataFlowProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DataFlowProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            ScienceToDoTopAppBar(
                title = uiState.dataFlow?.name ?: "DataFlow Profile",
                navigateUp = navController::navigateUp
            )
        },
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            Text(text = uiState.dataFlow?.name ?: "404")
        }
    }


}