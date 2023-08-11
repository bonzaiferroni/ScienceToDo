package com.bonsai.sciencetodo.data

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun toVariableType(value: Int): VariableType = VariableType.fromInt(value)

    @TypeConverter
    fun toInteger(value: VariableType): Int = value.intValue
}