package com.bonsai.sciencetodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.StringValue

@Dao
interface StringValueDao {
    @Query("SELECT * FROM string_value")
    suspend fun getAll(): List<StringValue>

    @Query("SELECT * FROM string_value WHERE id = :id")
    suspend fun getById(id: Int): StringValue

    @Query("SELECT * FROM string_value WHERE name LIKE :name")
    suspend fun getByName(name: String): List<StringValue>

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