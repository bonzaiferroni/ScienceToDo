package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.Variable
import kotlinx.coroutines.flow.Flow

@Dao
interface VariableDao {
    @Query("SELECT * FROM variable")
    fun getAll(): Flow<List<Variable>>

    @Query("SELECT * FROM variable WHERE id = :id")
    fun getById(id: Int): Flow<Variable>

    @Query("SELECT * FROM variable where dataset_id = :id")
    fun getByFlowId(id: Int): Flow<List<Variable>>

    @Query("SELECT * FROM variable WHERE name LIKE :name")
    fun getByName(name: String): Flow<Variable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(variable: Variable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(variables: List<Variable>)

    @Update
    suspend fun update(variable: Variable)

    @Delete
    suspend fun delete(variable: Variable)

    @Query("DELETE FROM variable")
    suspend fun deleteAll()
}