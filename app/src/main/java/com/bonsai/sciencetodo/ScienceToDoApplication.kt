package com.bonsai.sciencetodo

import android.app.Application
import com.bonsai.sciencetodo.data.AppDatabase
import com.bonsai.sciencetodo.data.DataRepository
import com.bonsai.sciencetodo.data.ObservationRepository

class ScienceToDoApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val observationRepository: ObservationRepository by lazy {
        ObservationRepository(
            database.observationDao(),
            database.stringValueDao(),
            database.intValueDao(),
            database.floatValueDao(),
            database.booleanValueDao(),
        )
    }
    val dataRepository: DataRepository by lazy {
        DataRepository(
            database.dataFlowDao(),
            database.observationDao(),
            database.variableDao(),
            database.stringValueDao(),
            database.intValueDao(),
            database.floatValueDao(),
            database.booleanValueDao()
        )
    }
}