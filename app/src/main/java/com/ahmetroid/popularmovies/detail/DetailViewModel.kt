package com.ahmetroid.popularmovies.detail

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmetroid.popularmovies.R
import com.ahmetroid.popularmovies.base.BaseViewModel
import com.ahmetroid.popularmovies.data.model.Movie
import com.ahmetroid.popularmovies.util.formatReleaseDate

class DetailViewModel(resources: Resources, movie: Movie) : BaseViewModel() {
    val title = movie.title
    val posterPath = movie.posterPath
    val backdropPath = movie.backdropPath
    val releaseDate = formatReleaseDate(movie.releaseDate)
    val voteAverage = resources.getString(R.string.vote_average_text, movie.voteAverage)
    val overview = movie.overview
}

class DetailViewModelFactory(
    private val resources: Resources,
    private val movie: Movie
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DetailViewModel(resources, movie) as T
    }
}