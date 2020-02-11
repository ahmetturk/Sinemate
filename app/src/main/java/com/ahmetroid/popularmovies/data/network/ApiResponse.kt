package com.ahmetroid.popularmovies.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    var results: List<T>
)