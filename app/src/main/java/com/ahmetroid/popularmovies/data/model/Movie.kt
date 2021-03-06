package com.ahmetroid.popularmovies.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class Movie(
    @PrimaryKey
    val id: String,
    val title: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "poster_path") val posterPath: String?,
    val overview: String,
    @Json(name = "vote_average") val voteAverage: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    val page: Int = 0
) : Parcelable