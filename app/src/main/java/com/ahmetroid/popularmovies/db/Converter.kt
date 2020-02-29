package com.ahmetroid.popularmovies.db

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun intListToString(list: List<Int>): String = list.joinToString()

    @TypeConverter
    fun stringToIntList(value: String): List<Int> = value.split(", ").map { it.toInt() }

}