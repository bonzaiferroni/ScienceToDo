package com.bonsai.sciencetodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "int_value")
data class IntValue(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val name: String,
    override val value: Int,
    @ColumnInfo(name = "data_flow_id")
    override val dataFlowId: Int,
    @ColumnInfo(name = "variable_id")
    override val variableId: Int
) : DataValue<Int>

