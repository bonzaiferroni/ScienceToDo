package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.FloatValue
import kotlinx.coroutines.flow.Flow

@Dao
interface FloatValueDao {
    @Query("SELECT * FROM float_value")
    fun getAll(): Flow<List<FloatValue>>

    @Query("SELECT * FROM float_value WHERE id = :id")
    fun getById(id: Int): Flow<FloatValue>

    @Query("SELECT * FROM float_value WHERE observation_id = :id")
    fun getByObservationId(id: Int): Flow<List<FloatValue>>

    @Query("SELECT * FROM float_value WHERE variable_id = :id")
    fun getByVariableId(id: Int): Flow<List<FloatValue>>

    @Query("SELECT * FROM float_value " +
            "WHERE variable_id = :id " +
            "ORDER BY id DESC " +
            "LIMIT :count")
    fun getByVariableId(id: Int, count: Int): Flow<List<FloatValue>>

    @Query("SELECT COUNT(*) FROM float_value WHERE variable_id = :id")
    fun getCountByVariableId(id: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(floatValue: FloatValue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(floatValues: List<FloatValue>)

    @Update
    suspend fun update(floatValue: FloatValue)

    @Delete
    suspend fun delete(floatValue: FloatValue)

    @Query("DELETE FROM float_value")
    suspend fun deleteAll()
}