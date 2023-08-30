package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.Routine
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {
    @Query("SELECT * FROM Routine")
    fun getAll(): Flow<List<Routine>>

    @Query("SELECT * FROM Routine WHERE id = :id")
    fun getById(id: Int): Flow<Routine>

    @Query("SELECT * FROM Routine where dataset_id = :id")
    fun getByDatasetId(id: Int): Flow<List<Routine>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(Routine: Routine): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(Routines: List<Routine>)

    @Update
    suspend fun update(Routine: Routine)

    @Delete
    suspend fun delete(Routine: Routine)

    @Query("DELETE FROM Routine")
    suspend fun deleteAll()
}