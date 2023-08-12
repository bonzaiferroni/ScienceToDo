package com.bonsai.sciencetodo

import android.app.Application
import com.bonsai.sciencetodo.data.AppDatabase
import com.bonsai.sciencetodo.data.ObservationManager

class ScienceToDoApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val observationManager: ObservationManager by lazy {
        ObservationManager(
            database.observationDao(),
            database.stringValueDao(),
            database.intValueDao()
        )
    }
}