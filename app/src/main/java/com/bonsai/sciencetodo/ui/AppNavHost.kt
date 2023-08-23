package com.bonsai.sciencetodo.ui

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bonsai.sciencetodo.ui.common.StdTopAppBar
import com.bonsai.sciencetodo.ui.dataset.DatasetScreen
import com.bonsai.sciencetodo.ui.dataview.DataViewScreen
import com.bonsai.sciencetodo.ui.enums.EnumScreen
import com.bonsai.sciencetodo.ui.home.HomeScreen

sealed interface AppScreens {
    val name: String

    data object Home : AppScreens {
        override val name = "home"
    }

    data object Dataset : AppScreens {
        override val name = "dataset"
        val routeWithArgs = "$name/{$datasetIdArg}"

        fun getRoute(datasetId: Int) = "$name/${datasetId}"
        fun getNavArg() = getIntNavArg(datasetIdArg)
    }

    data object DataView : AppScreens {
        override val name = "dataView"
        val routeWithArgs = "$name/{$datasetIdArg}"

        fun getRoute(datasetId: Int) = "$name/${datasetId}"
        fun getNavArg() = getIntNavArg(datasetIdArg)
    }

    data object Enum : AppScreens {
        override val name = "enum"
    }

    fun getIntNavArg(argName: String): NamedNavArgument {
        return navArgument(argName) { type = NavType.IntType }
    }

    companion object {
        const val datasetIdArg = "datasetId"
    }
}

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
            arguments = listOf(AppScreens.Dataset.getNavArg())
        ) {
            DatasetScreen(navController = navController)
        }
        composable(
            route = AppScreens.DataView.routeWithArgs,
            arguments = listOf(AppScreens.DataView.getNavArg())
        ) {
            DataViewScreen(navController = navController)
        }
        composable(
            route = AppScreens.Enum.name
        ) {
            EnumScreen(navController = navController)
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