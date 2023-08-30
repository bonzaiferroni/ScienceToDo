package com.bonsai.sciencetodo.ui.routine

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.ui.AppScreens
import com.bonsai.sciencetodo.ui.common.StdTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineScreen(
    navController: NavController?,
    viewModel: RoutineVm = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            StdTopAppBar(
                title = "Routines",
                navigateUp = { navController?.navigateUp() },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController?.navigate(AppScreens.NewRoutine.name)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Create Routine")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.gap_medium))
            ) {
                items(uiState.routines) { routineObjects ->
                    RoutineCard(routineObjects)
                }
            }
        }
    }
}

@Composable
fun RoutineCard(
    routineObjects: RoutineObjects,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = routineObjects.dataset.name)
    }
}
