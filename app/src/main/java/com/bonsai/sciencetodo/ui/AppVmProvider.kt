package com.bonsai.sciencetodo.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bonsai.sciencetodo.ScienceToDoApplication
import com.bonsai.sciencetodo.ui.dataset.DatasetVm
import com.bonsai.sciencetodo.ui.dataview.DataViewVm
import com.bonsai.sciencetodo.ui.enums.EnumVm
import com.bonsai.sciencetodo.ui.home.HomeVm

object AppVmProvider {

    val Factory = viewModelFactory {
        initializer {
            HomeVm(
                scienceToDoApplication().database.datasetDao(),
                scienceToDoApplication().database.variableDao(),
                scienceToDoApplication().observationRepository,
            )
        }
        initializer {
            DatasetVm(
                this.createSavedStateHandle(),
                scienceToDoApplication().database.datasetDao(),
                scienceToDoApplication().database.variableDao(),
                scienceToDoApplication().observationRepository,
                scienceToDoApplication().enumRepository,
            )
        }
        initializer {
            DataViewVm(
                this.createSavedStateHandle(),
                scienceToDoApplication().dataRepository,
            )
        }
        initializer {
            EnumVm(
                scienceToDoApplication().enumRepository,
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