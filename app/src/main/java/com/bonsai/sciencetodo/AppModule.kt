package com.bonsai.sciencetodo

import android.content.Context
import androidx.room.Room
import com.bonsai.sciencetodo.data.AppDatabase
import com.bonsai.sciencetodo.data.DataRepository
import com.bonsai.sciencetodo.data.EnumRepository
import com.bonsai.sciencetodo.data.ObservationRepository
import com.bonsai.sciencetodo.data.dao.BooleanValueDao
import com.bonsai.sciencetodo.data.dao.DatasetDao
import com.bonsai.sciencetodo.data.dao.EnumValueDao
import com.bonsai.sciencetodo.data.dao.FloatValueDao
import com.bonsai.sciencetodo.data.dao.IntValueDao
import com.bonsai.sciencetodo.data.dao.ObservationDao
import com.bonsai.sciencetodo.data.dao.StringValueDao
import com.bonsai.sciencetodo.data.dao.VariableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "science-todo"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDatasetDao(appDatabase: AppDatabase): DatasetDao {
        return appDatabase.datasetDao()
    }

    @Provides
    @Singleton
    fun provideObservationDao(appDatabase: AppDatabase): ObservationDao {
        return appDatabase.observationDao()
    }

    @Provides
    @Singleton
    fun provideVariableDao(appDatabase: AppDatabase): VariableDao {
        return appDatabase.variableDao()
    }

    @Provides
    @Singleton
    fun provideStringValueDao(appDatabase: AppDatabase): StringValueDao {
        return appDatabase.stringValueDao()
    }

    @Provides
    @Singleton
    fun provideIntValueDao(appDatabase: AppDatabase): IntValueDao {
        return appDatabase.intValueDao()
    }

    @Provides
    @Singleton
    fun provideFloatValueDao(appDatabase: AppDatabase): FloatValueDao {
        return appDatabase.floatValueDao()
    }

    @Provides
    @Singleton
    fun provideBooleanValueDao(appDatabase: AppDatabase): BooleanValueDao {
        return appDatabase.booleanValueDao()
    }

    @Provides
    @Singleton
    fun provideEnumValueDao(appDatabase: AppDatabase): EnumValueDao {
        return appDatabase.enumValueDao()
    }

    @Provides
    @Singleton
    fun provideDataRepository(appDatabase: AppDatabase): DataRepository {
        return DataRepository(
            appDatabase.datasetDao(), appDatabase.observationDao(), appDatabase.variableDao(),
            appDatabase.stringValueDao(), appDatabase.intValueDao(), appDatabase.floatValueDao(),
            appDatabase.booleanValueDao(), appDatabase.enumValueDao(),
        )
    }

    @Provides
    @Singleton
    fun provideObservationRepository(appDatabase: AppDatabase): ObservationRepository {
        return ObservationRepository(
            appDatabase.observationDao(), appDatabase.stringValueDao(), appDatabase.intValueDao(),
            appDatabase.floatValueDao(), appDatabase.booleanValueDao(), appDatabase.enumValueDao()
        )
    }

    @Provides
    @Singleton
    fun provideEnumRepository(appDatabase: AppDatabase): EnumRepository {
        return EnumRepository(
            appDatabase.enumerationDao(), appDatabase.enumeratorDao(), appDatabase.enumValueDao(),
            appDatabase.enumVarJoinDao()
        )
    }
}