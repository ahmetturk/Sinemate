package com.ahmetroid.popularmovies.db

import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    @Test
    fun intListToString() {
        val result = Converters().intListToString(emptyList())
        assertEquals(result, "")
    }

    @Test
    fun stringToIntList() {
        val result = Converters().stringToIntList("")
        assertEquals(result, emptyList<Int>())
    }
}