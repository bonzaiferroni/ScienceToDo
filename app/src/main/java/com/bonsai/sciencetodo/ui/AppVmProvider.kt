package com.bonsai.sciencetodo.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bonsai.sciencetodo.ScienceToDoApplication
import com.bonsai.sciencetodo.ui.dataflowprofile.DataProfileVm
import com.bonsai.sciencetodo.ui.dataview.DataViewVm
import com.bonsai.sciencetodo.ui.home.HomeVm

object AppVmProvider {

    val Factory = viewModelFactory {
        initializer {
            HomeVm(
                scienceToDoApplication().database.dataFlowDao(),
                scienceToDoApplication().database.variableDao(),
                scienceToDoApplication().observationRepository,
            )
        }
        initializer {
            DataProfileVm(
                this.createSavedStateHandle(),
                scienceToDoApplication().database.dataFlowDao(),
                scienceToDoApplication().database.variableDao(),
                scienceToDoApplication().observationRepository,
            )
        }
        initializer {
            DataViewVm(
                this.createSavedStateHandle(),
                scienceToDoApplication().dataRepository,
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [scienceToDoApplication].
 */
fun CreationExtras.scienceToDoApplication(): ScienceToDoApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ScienceToDoApplication)