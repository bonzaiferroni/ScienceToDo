package com.bonsai.sciencetodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bonsai.sciencetodo.data.dao.BooleanValueDao
import com.bonsai.sciencetodo.data.dao.DatasetDao
import com.bonsai.sciencetodo.data.dao.EnumValueDao
import com.bonsai.sciencetodo.data.dao.EnumVarJoinDao
import com.bonsai.sciencetodo.data.dao.EnumerationDao
import com.bonsai.sciencetodo.data.dao.EnumeratorDao
import com.bonsai.sciencetodo.data.dao.FloatValueDao
import com.bonsai.sciencetodo.data.dao.IntValueDao
import com.bonsai.sciencetodo.data.dao.ObservationDao
import com.bonsai.sciencetodo.data.dao.RoutineDao
import com.bonsai.sciencetodo.data.dao.StringValueDao
import com.bonsai.sciencetodo.data.dao.VariableDao
import com.bonsai.sciencetodo.model.BooleanValue
import com.bonsai.sciencetodo.model.Dataset
import com.bonsai.sciencetodo.model.EnumValue
import com.bonsai.sciencetodo.model.EnumVarJoin
import com.bonsai.sciencetodo.model.Enumeration
import com.bonsai.sciencetodo.model.Enumerator
import com.bonsai.sciencetodo.model.FloatValue
import com.bonsai.sciencetodo.model.IntValue
import com.bonsai.sciencetodo.model.Observation
import com.bonsai.sciencetodo.model.Routine
import com.bonsai.sciencetodo.model.StringValue
import com.bonsai.sciencetodo.model.Variable

@Database(
    entities = [
        Dataset::class, Variable::class, Observation::class,
        Enumeration::class, Enumerator::class, EnumValue::class, EnumVarJoin::class,
        StringValue::class, IntValue::class, FloatValue::class, BooleanValue::class,
        Routine::class],
    version = 14
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun datasetDao(): DatasetDao
    abstract fun variableDao(): VariableDao
    abstract fun observationDao(): ObservationDao
    abstract fun stringValueDao(): StringValueDao
    abstract fun intValueDao(): IntValueDao
    abstract fun floatValueDao(): FloatValueDao
    abstract fun booleanValueDao(): BooleanValueDao
    abstract fun enumerationDao(): EnumerationDao
    abstract fun enumeratorDao(): EnumeratorDao
    abstract fun enumValueDao(): EnumValueDao
    abstract fun enumVarJoinDao(): EnumVarJoinDao
    abstract fun routineDao(): RoutineDao
}