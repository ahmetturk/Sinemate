package com.ahmetroid.popularmovies.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MovieDetail(
    val videos: VideosResponse
) : Parcelable