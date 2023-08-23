package com.bonsai.sciencetodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "enumerator",
    foreignKeys = [
        ForeignKey(
            entity = Enumeration::class,
            parentColumns = ["id"],
            childColumns = ["enumeration_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("enumeration_id")]
)
data class Enumerator(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "enumeration_id")
    val enumerationId: Int,
    val name: String,
    val orderIndex: Int,
)