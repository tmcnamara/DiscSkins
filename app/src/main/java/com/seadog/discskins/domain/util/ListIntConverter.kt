package com.seadog.discskins.domain.util

import android.util.Log
import androidx.room.TypeConverter

class ListIntConverter {
    @TypeConverter
    fun fromString(value: String): List<Int> {
        val strList = value.replace(" ", "")
            .trimStart('[')
            .trimEnd(']')

      return if (strList.isNullOrEmpty())
          listOf()
      else strList.split(",").map {
                it.toInt()
            }
    }

    @TypeConverter
    fun toString(value: List<Int>): String = value.toString()
}