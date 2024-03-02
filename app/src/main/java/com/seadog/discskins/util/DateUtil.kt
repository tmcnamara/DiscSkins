package com.seadog.discskins.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtil {
    private val formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")
    private val formatter2 = DateTimeFormatter.ofPattern("MMM dd, yyyy hhmm")

    fun reformat(date: String): String {
        val realDate = LocalDateTime.parse(date, formatter1)
        return formatter2.format(realDate)
    }
}