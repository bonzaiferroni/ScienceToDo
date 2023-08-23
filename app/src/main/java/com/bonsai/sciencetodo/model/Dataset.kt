package com.bonsai.sciencetodo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dataset")
data class Dataset(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
)
