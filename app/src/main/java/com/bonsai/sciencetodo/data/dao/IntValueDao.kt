package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.IntValue
import kotlinx.coroutines.flow.Flow

@Dao
interface IntValueDao {
    @Query("SELECT * FROM int_value")
    fun getAll(): Flow<List<IntValue>>

    @Query("SELECT * FROM int_value WHERE id = :id")
    fun getById(id: Int): Flow<IntValue>

    @Query("SELECT * FROM int_value WHERE observation_id = :id")
    fun getByObservationId(id: Int): Flow<List<IntValue>>

    @Query("SELECT * FROM int_value WHERE variable_id = :id ")
    fun getByVariableId(id: Int): Flow<List<IntValue>>

    @Query("SELECT * FROM int_value " +
            "WHERE variable_id = :id " +
            "ORDER BY id DESC " +
            "LIMIT :count")
    fun getByVariableId(id: Int, count: Int): Flow<List<IntValue>>

    @Query("SELECT COUNT(*) FROM int_value WHERE variable_id = :id")
    fun getCountByVariableId(id: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(intValue: IntValue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(intValues: List<IntValue>)

    @Update
    suspend fun update(intValue: IntValue)

    @Delete
    suspend fun delete(intValue: IntValue)

    @Query("DELETE FROM int_value")
    suspend fun deleteAll()
}