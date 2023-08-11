package com.bonsai.sciencetodo.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bonsai.sciencetodo.R
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.ui.dataflowprofile.DataFlowProfileScreen
import com.bonsai.sciencetodo.ui.home.HomeScreen

sealed interface AppScreens {
    val name: String

    data object Home : AppScreens {
        override val name = "home"
    }

    data object DataFlowProfile : AppScreens {
        override val name = "dataFlowProfile"
        const val dataFlowIdArg = "dataFlowId"
        val routeWithArgs = "$name/{$dataFlowIdArg}"

        fun getRoute(dataFlow: DataFlow) = "$name/${dataFlow.id}"
        fun getNavArg() = getIntNavArg(dataFlowIdArg)
    }

    fun getIntNavArg(argName: String): NamedNavArgument {
        return navArgument(argName) { type = NavType.IntType }
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
            route = AppScreens.DataFlowProfile.routeWithArgs,
            arguments = listOf(AppScreens.DataFlowProfile.getNavArg())
        ) {
            DataFlowProfileScreen(navController = navController)
        }
    }
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScienceToDoTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (navigateUp != null) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        })
}