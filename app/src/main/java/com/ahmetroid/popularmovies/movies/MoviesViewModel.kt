package com.ahmetroid.popularmovies.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ahmetroid.popularmovies.base.BaseViewModel
import com.ahmetroid.popularmovies.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val POSTER_URL = "https://image.tmdb.org/t/p/w342"
private const val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

class MoviesViewModel(private val repository: Repository) : BaseViewModel() {
    private var loading = false

    val movies = repository.getMovies()

    private val page
        get() = (if (movies.value.isNullOrEmpty()) 0 else (movies.value!!.size / 20)) + 1

    fun onLoadMore() {
        if (loading.not()) {
            loading = true

            viewModelScope.launch(Dispatchers.IO) {
                val newMovies = repository.loadMovies(page).map {
                    it.copy(
                        posterPath = POSTER_URL + it.posterPath,
                        backdropPath = BACKDROP_URL + it.backdropPath
                    )
                }

                repository.insertMoviesToDb(newMovies)
                loading = false
            }
        }
    }
}

class MovieViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MoviesViewModel(repository) as T
    }
}