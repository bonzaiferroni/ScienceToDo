package com.bonsai.sciencetodo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "enumeration")
data class Enumeration(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
)