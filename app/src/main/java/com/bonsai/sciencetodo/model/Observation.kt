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
            entity = DataFlow::class,
            parentColumns = ["id"],
            childColumns = ["data_flow_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("data_flow_id")]
)
data class Observation (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "data_flow_id")
    val dataFlowId: Int,
    val instant: Instant,
    )