package com.bonsai.sciencetodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "float_value")
data class FloatValue(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    @ColumnInfo(name = "variable_id")
    override val variableId: Int,
    @ColumnInfo(name = "observation_id")
    override val observationId: Int,
    override val value: Float,
) : DataValue<Float>

