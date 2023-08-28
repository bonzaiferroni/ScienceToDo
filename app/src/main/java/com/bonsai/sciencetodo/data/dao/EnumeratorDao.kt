package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.Enumerator
import kotlinx.coroutines.flow.Flow

@Dao
interface EnumeratorDao {
    @Query("SELECT * FROM enumerator")
    fun getAll(): Flow<List<Enumerator>>

    @Query("SELECT * FROM enumerator WHERE id = :id")
    fun getById(id: Int): Flow<Enumerator>

    @Query("SELECT * FROM enumerator WHERE name LIKE :name")
    fun getByName(name: String): Flow<Enumerator>

    @Query("SELECT * FROM enumerator WHERE enumeration_id = :enumerationId")
    fun getByEnumerationId(enumerationId: Int): Flow<List<Enumerator>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(enumerator: Enumerator)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(enumerators: List<Enumerator>)

    @Update
    suspend fun update(enumerator: Enumerator)

    @Update
    suspend fun updateAll(enumerators: List<Enumerator>): Int

    @Delete
    suspend fun delete(enumerator: Enumerator)

    @Query("DELETE FROM enumerator")
    suspend fun deleteAll()
}