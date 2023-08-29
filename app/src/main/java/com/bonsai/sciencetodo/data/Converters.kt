package com.bonsai.sciencetodo.data

import androidx.room.TypeConverter
import com.bonsai.sciencetodo.model.VariableType
import java.time.Instant
import java.time.LocalTime

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? =
        if (value != null) Instant.ofEpochMilli(value) else { null }

    @TypeConverter
    fun dateToTimestamp(instant: Instant?): Long? = instant?.toEpochMilli()

    @TypeConverter
    fun toVariableType(value: Int): VariableType = VariableType.fromInt(value)

    @TypeConverter
    fun toInteger(value: VariableType): Int = value.dbValue

    @TypeConverter
    fun fromLocalTimeToSeconds(localTime: LocalTime?): Int? {
        return localTime?.toSecondOfDay()
    }

    @TypeConverter
    fun fromSecondsToLocalTime(secondOfDay: Int?): LocalTime? {
        return secondOfDay?.let { LocalTime.ofSecondOfDay(it.toLong()) }
    }
}