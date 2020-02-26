package com.ahmetroid.popularmovies.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun formatReleaseDate(releaseDate: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return try {
        val date = dateFormat.parse(releaseDate)!!
        return DateFormat.getDateInstance(DateFormat.LONG).format(date)
    } catch (e: ParseException) {
        releaseDate
    }
}