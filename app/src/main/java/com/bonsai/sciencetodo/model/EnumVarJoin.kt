package com.bonsai.sciencetodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "enum_var_join",
    primaryKeys = ["variable_id", "enumeration_id"],
    foreignKeys = [
        ForeignKey(
            entity = Variable::class,
            parentColumns = ["id"],
            childColumns = ["variable_id"],
            // TODO: Figure out onDelete for enums
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Enumeration::class,
            parentColumns = ["id"],
            childColumns = ["enumeration_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class EnumVarJoin (
    @ColumnInfo(name = "variable_id")
    val variableId: Int,
    @ColumnInfo(name = "enumeration_id")
    val enumerationId: Int,
)