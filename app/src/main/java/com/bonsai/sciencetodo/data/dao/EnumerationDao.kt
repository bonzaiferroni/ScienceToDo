package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.Enumeration
import kotlinx.coroutines.flow.Flow

@Dao
interface EnumerationDao {
    @Query("SELECT * FROM enumeration")
    fun getAll(): Flow<List<Enumeration>>

    @Query("SELECT * FROM enumeration WHERE id = :id")
    fun getById(id: Int): Flow<Enumeration>

    @Query("SELECT * FROM enumeration WHERE name LIKE :name")
    fun getByName(name: String): Flow<Enumeration>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(enumeration: Enumeration)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(enumerations: List<Enumeration>)

    @Update
    suspend fun update(enumeration: Enumeration)

    @Delete
    suspend fun delete(enumeration: Enumeration)

    @Query("DELETE FROM enumeration")
    suspend fun deleteAll()
}