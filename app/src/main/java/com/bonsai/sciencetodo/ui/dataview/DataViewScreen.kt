package com.bonsai.sciencetodo.ui.dataview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.ui.AppVmProvider
import com.bonsai.sciencetodo.ui.ScienceToDoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataViewScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: DataViewVm = viewModel(factory = AppVmProvider.Factory)
) {
    Scaffold(
        topBar = {
            ScienceToDoTopAppBar(title = "Data: ")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

        }
    }
}