package com.bonsai.sciencetodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "int_value")
data class IntValue(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val value: Int,
    @ColumnInfo(name = "variable_id")
    override val variableId: Int,
    @ColumnInfo(name = "observation_id")
    override val observationId: Int
) : DataValue<Int>

