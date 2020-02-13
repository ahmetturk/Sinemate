package com.ahmetroid.popularmovies.movies

import androidx.lifecycle.*
import com.ahmetroid.popularmovies.base.BaseViewModel
import com.ahmetroid.popularmovies.data.Repository
import com.ahmetroid.popularmovies.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: Repository) : BaseViewModel() {

    private val movieList = mutableListOf<Movie>()
    private var page = 0
    private var loading = false

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {
        onLoadMore()
    }

    fun onLoadMore() {
        if (loading.not()) {
            loading = true
            page++

            viewModelScope.launch(Dispatchers.IO) {
                val newMovies = repository.getMovies(page)
                movieList.addAll(newMovies)
                _movies.postValue(movieList)
                loading = false
            }
        }
    }
}

// TODO Create generic ViewModelFactory class
@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(Repository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}