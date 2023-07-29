package com.bonsai.sciencetodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.Variable

@Dao
interface VariableDao {
    @Query("SELECT * FROM variable")
    suspend fun getAll(): List<Variable>

    @Query("SELECT * FROM variable WHERE id = :id")
    suspend fun getById(id: Int): Variable

    @Query("SELECT * FROM variable WHERE name LIKE :name")
    suspend fun getByName(name: String): List<Variable>

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