package com.bonsai.sciencetodo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_flow")
data class DataFlow(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
)
