package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.DataFlow
import kotlinx.coroutines.flow.Flow

@Dao
interface DataFlowDao {
    @Query("SELECT * FROM data_flow")
    fun getAll(): Flow<List<DataFlow>>

    @Query("SELECT * FROM data_flow WHERE id = :id")
    fun getById(id: Int): Flow<DataFlow>

    @Query("SELECT * FROM data_flow WHERE name LIKE :name")
    fun getByName(name: String): Flow<DataFlow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dataFlow: DataFlow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dataFlows: List<DataFlow>)

    @Update
    suspend fun update(dataFlow: DataFlow)

    @Delete
    suspend fun delete(dataFlow: DataFlow)

    @Query("DELETE FROM data_flow")
    suspend fun deleteAll()
}