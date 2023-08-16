package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.EnumValue
import kotlinx.coroutines.flow.Flow

@Dao
interface EnumValueDao {
    @Query("SELECT * FROM enum_value")
    fun getAll(): Flow<List<EnumValue>>

    @Query("SELECT * FROM enum_value WHERE id = :id")
    fun getById(id: Int): Flow<EnumValue>

    @Query("SELECT * FROM enum_value WHERE observation_id = :id")
    fun getByObservationId(id: Int): Flow<List<EnumValue>>

    @Query("SELECT * FROM enum_value WHERE variable_id = :id")
    fun getByVariableId(id: Int): Flow<List<EnumValue>>

    @Query("SELECT COUNT(*) FROM enum_value WHERE variable_id = :id")
    fun getCountByVariableId(id: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(enumValue: EnumValue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(enumValues: List<EnumValue>)

    @Update
    suspend fun update(enumValue: EnumValue)

    @Delete
    suspend fun delete(enumValue: EnumValue)

    @Query("DELETE FROM enum_value")
    suspend fun deleteAll()
}