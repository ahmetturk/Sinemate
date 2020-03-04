package com.ahmetroid.popularmovies.movies

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ahmetroid.popularmovies.Event
import com.ahmetroid.popularmovies.base.BaseViewModel
import com.ahmetroid.popularmovies.data.Repository
import com.ahmetroid.popularmovies.data.Resource
import com.ahmetroid.popularmovies.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    companion object {
        private const val KEY_LOAD_TIME = "loadTime"
        private const val ONE_DAY = 24 * 60 * 60 * 1000L
    }

    val movies = repository.getMovies().toLiveData(
        pageSize = 20,
        boundaryCallback = object : PagedList.BoundaryCallback<Movie>() {
            override fun onZeroItemsLoaded() {
                loadMovies(1)
            }

            override fun onItemAtFrontLoaded(itemAtFront: Movie) {
                val lastLoadTime = sharedPreferences.getLong(KEY_LOAD_TIME, 0)
                if (lastLoadTime + ONE_DAY <= System.currentTimeMillis()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        if (repository.fetchMovies(1) is Resource.Success) {
                            repository.deleteMovies()
                        }
                    }
                }
            }

            override fun onItemAtEndLoaded(itemAtEnd: Movie) {
                loadMovies(itemAtEnd.page + 1)

            }
        })

    private fun loadMovies(pageNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val movies = repository.fetchMovies(pageNumber)) {
                is Resource.Success -> {
                    repository.insertMovies(movies.data)
                    if (pageNumber == 1) {
                        sharedPreferences.edit {
                            putLong(KEY_LOAD_TIME, System.currentTimeMillis())
                        }
                    }
                }
                is Resource.Failure -> {
                    page = pageNumber
                    _showSnackbar.postValue(Event(Unit))
                }
            }
        }
    }

    private val _showSnackbar = MutableLiveData<Event<Unit>>()
    val showSnackbar: LiveData<Event<Unit>>
        get() = _showSnackbar

    private var page = 1

    fun retryClicked() = loadMovies(page)
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