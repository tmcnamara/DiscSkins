package com.seadog.discskins.domain.util

import androidx.room.TypeConverter

class ListDoubleConverter {
    @TypeConverter
    fun fromString(value: String): List<Double> =
        value.split(",").map {
            it.toDouble()
        }

    @TypeConverter
    fun toString(value: List<Double>): String = value.toString()
}