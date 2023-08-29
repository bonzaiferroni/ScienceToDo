package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM Reminder")
    fun getAll(): Flow<List<Reminder>>

    @Query("SELECT * FROM Reminder WHERE id = :id")
    fun getById(id: Int): Flow<Reminder>

    @Query("SELECT * FROM Reminder where dataset_id = :id")
    fun getByDatasetId(id: Int): Flow<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(Reminder: Reminder): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(Reminders: List<Reminder>)

    @Update
    suspend fun update(Reminder: Reminder)

    @Delete
    suspend fun delete(Reminder: Reminder)

    @Query("DELETE FROM Reminder")
    suspend fun deleteAll()
}