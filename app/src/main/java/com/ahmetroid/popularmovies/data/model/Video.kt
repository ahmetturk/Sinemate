package com.ahmetroid.popularmovies.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val image: String?
) : Parcelable