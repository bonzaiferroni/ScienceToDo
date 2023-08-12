package com.bonsai.sciencetodo

import android.app.Application
import com.bonsai.sciencetodo.data.AppDatabase
import com.bonsai.sciencetodo.data.DataDaoManager

class ScienceToDoApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val dataDaoManager: DataDaoManager by lazy {
        DataDaoManager(
            database.observationDao(),
            database.stringValueDao(),
            database.intValueDao()
        )
    }
}