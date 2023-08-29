package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.StringValue
import kotlinx.coroutines.flow.Flow

@Dao
interface StringValueDao {
    @Query("SELECT * FROM string_value")
    fun getAll(): Flow<List<StringValue>>

    @Query("SELECT * FROM string_value WHERE id = :id")
    fun getById(id: Int): Flow<StringValue>

    @Query("SELECT * FROM string_value WHERE observation_id = :id")
    fun getByObservationId(id: Int): Flow<List<StringValue>>

    @Query("SELECT * FROM string_value WHERE variable_id = :id")
    fun getByVariableId(id: Int): Flow<List<StringValue>>

    @Query("SELECT * FROM string_value " +
            "WHERE variable_id = :id " +
            "ORDER BY id DESC " +
            "LIMIT :count")
    fun getByVariableId(id: Int, count: Int): Flow<List<StringValue>>

    @Query("SELECT COUNT(*) FROM string_value WHERE variable_id = :id")
    fun getCountByVariableId(id: Int): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stringValue: StringValue)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stringValues: List<StringValue>)

    @Update
    suspend fun update(stringValue: StringValue)

    @Delete
    suspend fun delete(stringValue: StringValue)

    @Query("DELETE FROM string_value")
    suspend fun deleteAll()
}