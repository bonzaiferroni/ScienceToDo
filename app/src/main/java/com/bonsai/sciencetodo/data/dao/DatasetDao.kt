package com.bonsai.sciencetodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bonsai.sciencetodo.model.Dataset
import kotlinx.coroutines.flow.Flow

@Dao
interface DatasetDao {
    @Query("SELECT * FROM dataset")
    fun getAll(): Flow<List<Dataset>>

    @Query("SELECT * FROM dataset WHERE id = :id")
    fun getById(id: Int): Flow<Dataset>

    @Query("SELECT * FROM dataset WHERE name LIKE :name")
    fun getByName(name: String): Flow<Dataset>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dataset: Dataset)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datasets: List<Dataset>)

    @Update
    suspend fun update(dataset: Dataset)

    @Delete
    suspend fun delete(dataset: Dataset)

    @Query("DELETE FROM dataset")
    suspend fun deleteAll()
}