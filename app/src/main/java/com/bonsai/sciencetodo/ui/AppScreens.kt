package com.bonsai.sciencetodo.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed interface AppScreens {
    val name: String

    data object Home : AppScreens {
        override val name = "home"
    }

    data object Dataset : AppScreens {
        override val name = "dataset"
        val routeWithArgs = "$name/{$datasetIdArg}"

        fun getRoute(datasetId: Int) = "$name/${datasetId}"
        fun getNavArgs() = listOf(getIntNavArg(datasetIdArg))
    }

    data object DataView : AppScreens {
        override val name = "dataView"
        val routeWithArgs = "$name/{$datasetIdArg}"

        fun getRoute(datasetId: Int) = "$name/${datasetId}"
        fun getNavArgs() = listOf(getIntNavArg(datasetIdArg))
    }

    data object Enum : AppScreens {
        override val name = "enum"
    }

    data object Observation : AppScreens {
        override val name = "observation"
        val routeWithArgs = "${name}/{$datasetIdArg}"
        fun getNavArgs() = listOf(getIntNavArg(datasetIdArg))

        fun openObservation(navController: NavController?, datasetId: Int) {
            navController?.navigate("${name}/${datasetId}")
        }
    }

    fun getIntNavArg(argName: String): NamedNavArgument {
        return navArgument(argName) { type = NavType.IntType }
    }

    companion object {
        const val datasetIdArg = "datasetId"
    }
}