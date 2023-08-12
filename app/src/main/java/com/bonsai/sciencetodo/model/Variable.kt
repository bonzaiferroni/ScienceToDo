package com.bonsai.sciencetodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "variable",
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
data class Variable(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "data_flow_id")
    val dataFlowId: Int,
    @ColumnInfo(name = "type")
    val type: VariableType,
)