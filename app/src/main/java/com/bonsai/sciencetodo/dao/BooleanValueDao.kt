package com.bonsai.sciencetodo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.BooleanValue
import kotlinx.coroutines.flow.Flow

@Dao
interface BooleanValueDao {
    @Query("SELECT * FROM boolean_value")
    fun getAll(): Flow<List<BooleanValue>>

    @Query("SELECT * FROM boolean_value WHERE id = :id")
    fun getById(id: Int): Flow<BooleanValue>

    @Query("SELECT * FROM boolean_value WHERE observation_id = :id")
    fun getByObservationId(id: Int): Flow<List<BooleanValue>>

    @Query("SELECT * FROM boolean_value WHERE variable_id = :id")
    fun getByVariableId(id: Int): Flow<List<BooleanValue>>

    @Query("SELECT COUNT(*) FROM boolean_value WHERE variable_id = :id")
    fun getCountByVariableId(id: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(booleanValue: BooleanValue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(booleanValues: List<BooleanValue>)

    @Update
    suspend fun update(booleanValue: BooleanValue)

    @Delete
    suspend fun delete(booleanValue: BooleanValue)

    @Query("DELETE FROM boolean_value")
    suspend fun deleteAll()
}