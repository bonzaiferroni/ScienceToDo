package com.bonsai.sciencetodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "observation",
    foreignKeys = [
        ForeignKey(
            entity = Dataset::class,
            parentColumns = ["id"],
            childColumns = ["dataset_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("dataset_id")]
)
data class Observation (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "dataset_id")
    val datasetId: Int,
    val instant: Instant,
    )