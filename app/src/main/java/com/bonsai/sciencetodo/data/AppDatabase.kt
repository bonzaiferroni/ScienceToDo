package com.bonsai.sciencetodo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bonsai.sciencetodo.model.DataFlow
import com.bonsai.sciencetodo.model.FloatValue
import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.model.Variable

@Database(
    entities = [
        DataFlow::class, Variable::class, Observation::class,
        StringValue::class, IntValue::class, FloatValue::class],
    version = 6
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataFlowDao(): DataFlowDao
    abstract fun variableDao(): VariableDao
    abstract fun observationDao(): ObservationDao
    abstract fun stringValueDao(): StringValueDao
    abstract fun intValueDao(): IntValueDao
    abstract fun floatValueDao(): FloatValueDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "science-todo"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    INSTANCE = it
                }
        }
    }
}