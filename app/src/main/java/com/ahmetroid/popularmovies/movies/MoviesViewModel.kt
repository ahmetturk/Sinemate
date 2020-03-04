package com.ahmetroid.popularmovies.movies

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ahmetroid.popularmovies.base.BaseViewModel
import com.ahmetroid.popularmovies.data.Repository
import com.ahmetroid.popularmovies.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    val movies = repository.getMovies().toLiveData(
        pageSize = 20,
        boundaryCallback = object : PagedList.BoundaryCallback<Movie>() {
            override fun onZeroItemsLoaded() {
                viewModelScope.launch(Dispatchers.IO) {
                    val movies = repository.fetchMovies(1)
                    repository.insertMovies(movies)
                    sharedPreferences.edit { putLong(KEY_LOAD_TIME, System.currentTimeMillis()) }
                }
            }

            override fun onItemAtFrontLoaded(itemAtFront: Movie) {
                val lastLoadTime =
                    sharedPreferences.getLong(KEY_LOAD_TIME, 0)

                if (lastLoadTime + ONE_DAY <= System.currentTimeMillis()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        val movies = repository.fetchMovies(1)
                        if (movies.isNotEmpty()) {
                            repository.deleteMovies()
                        }
                    }
                }
            }

            override fun onItemAtEndLoaded(itemAtEnd: Movie) {
                viewModelScope.launch(Dispatchers.IO) {
                    val movies = repository.fetchMovies(itemAtEnd.page + 1)
                    repository.insertMovies(movies)
                }
            }
        })

    companion object {
        private const val KEY_LOAD_TIME = "loadTime"
        private const val ONE_DAY = 60 * 1000L
    }
}

class MovieViewModelFactory(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MoviesViewModel(repository, sharedPreferences) as T
    }
}