package com.bonsai.sciencetodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.Observation
import kotlinx.coroutines.flow.Flow

@Dao
interface ObservationDao {
    @Query("SELECT * FROM observation")
    fun getAll(): Flow<List<Observation>>

    @Query("SELECT * FROM observation WHERE id = :id")
    fun getById(id: Int): Flow<Observation>

    @Query("SELECT * FROM observation where data_flow_id = :id")
    fun getByFlowId(id: Int): Flow<List<Observation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(observation: Observation): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(observations: List<Observation>)

    @Update
    suspend fun update(observation: Observation)

    @Delete
    suspend fun delete(observation: Observation)

    @Query("DELETE FROM observation")
    suspend fun deleteAll()
}