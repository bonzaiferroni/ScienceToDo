package com.bonsai.sciencetodo.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bonsai.sciencetodo.ScienceToDoApplication
import com.bonsai.sciencetodo.data.AppDatabase
import com.bonsai.sciencetodo.ui.dataflowprofile.DataFlowProfileViewModel
import com.bonsai.sciencetodo.ui.home.HomeViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                scienceToDoApplication().database.dataFlowDao()
            )
        }
        initializer {
            DataFlowProfileViewModel(
                this.createSavedStateHandle(),
                scienceToDoApplication().database.dataFlowDao(),
                scienceToDoApplication().database.variableDao(),
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