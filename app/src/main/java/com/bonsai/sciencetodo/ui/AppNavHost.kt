package com.bonsai.sciencetodo.ui

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bonsai.sciencetodo.ui.common.StdTopAppBar
import com.bonsai.sciencetodo.ui.dataset.DatasetScreen
import com.bonsai.sciencetodo.ui.dataview.DataViewScreen
import com.bonsai.sciencetodo.ui.enums.EnumScreen
import com.bonsai.sciencetodo.ui.home.HomeScreen
import com.bonsai.sciencetodo.ui.observation.ObservationScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.name,
        modifier = modifier
    ) {
        composable(route = AppScreens.Home.name) {
            HomeScreen(navController = navController)
        }
        composable(
            route = AppScreens.Dataset.routeWithArgs,
            arguments = AppScreens.Dataset.getNavArgs()
        ) {
            DatasetScreen(navController = navController)
        }
        composable(
            route = AppScreens.DataView.routeWithArgs,
            arguments = AppScreens.DataView.getNavArgs()
        ) {
            DataViewScreen(navController = navController)
        }
        composable(
            route = AppScreens.Enum.name
        ) {
            EnumScreen(navController = navController)
        }
        composable(
            route = AppScreens.Observation.routeWithArgs,
            arguments = AppScreens.Observation.getNavArgs(),
        ) {
            ObservationScreen(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewTopAppBar() {
    StdTopAppBar(
        title = "Preview",
    ) {
        DropdownMenuItem(
            text = { Text("Load") },
            onClick = {  }
        )
        DropdownMenuItem(
            text = { Text("Save") },
            onClick = {  }
        )
    }
}