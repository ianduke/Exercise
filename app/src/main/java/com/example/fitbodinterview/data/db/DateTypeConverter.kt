package com.example.fitbodinterview.data.db

import androidx.room.TypeConverter
import java.util.*

object DateTypeConverter {
    @TypeConverter
    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
}