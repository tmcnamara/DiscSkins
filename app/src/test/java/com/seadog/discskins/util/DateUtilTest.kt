package com.seadog.discskins.util

import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilTest {

    @Test
    fun test_reformat_am(){
        val dateUtil = DateUtil()
        val result = dateUtil.reformat("2022-02-26 9:59")
        assertEquals("Feb 26, 2022 9:59 AM", result)
    }

    @Test
    fun test_reformat_am_2(){
        val dateUtil = DateUtil()
        val result = dateUtil.reformat("2022-02-26 09:59")
        assertEquals("Feb 26, 2022 9:59 AM", result)
    }

    @Test
    fun test_reformat_am_3(){
        val dateUtil = DateUtil()
        val result = dateUtil.reformat("2022-02-26 10:59")
        assertEquals("Feb 26, 2022 10:59 AM", result)
    }

    @Test
    fun test_reformat_pm(){
        val dateUtil = DateUtil()
        val result = dateUtil.reformat("2022-01-29 21:32")
        assertEquals("Jan 29, 2022 9:32 PM", result)
    }
}