package com.bonsai.sciencetodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.LocalTime

@Entity(
    tableName = "reminder",
    foreignKeys = [
        ForeignKey(
            entity = Reminder::class,
            parentColumns = ["id"],
            childColumns = ["dataset_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("dataset_id")]
)
data class Reminder (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "dataset_id")
    val datasetId: Int,
    val lastRecorded: Instant,
    val intervalSeconds: Int?,
    val baseTime: LocalTime?,
)