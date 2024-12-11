package com.c242ps070.turuku.utils

import androidx.room.TypeConverter
import java.util.Date

class DatetimeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}