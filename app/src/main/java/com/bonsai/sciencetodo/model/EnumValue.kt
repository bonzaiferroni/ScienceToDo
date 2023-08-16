package com.bonsai.sciencetodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "enum_value",
    foreignKeys = [
        ForeignKey(
            entity = Enumerator::class,
            parentColumns = ["id"],
            childColumns = ["value"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("value")]
)
data class EnumValue(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    @ColumnInfo(name = "variable_id")
    override val variableId: Int,
    @ColumnInfo(name = "observation_id")
    override val observationId: Int,
    override val value: Int,
) : DataValue<Int>