package com.ahmetroid.popularmovies.movies

import androidx.lifecycle.*
import com.ahmetroid.popularmovies.base.BaseViewModel
import com.ahmetroid.popularmovies.data.Repository
import com.ahmetroid.popularmovies.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val POSTER_URL = "https://image.tmdb.org/t/p/w342"
private const val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

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
                val newMovies = repository.getMovies(page).map {
                    it.copy(
                        posterPath = POSTER_URL + it.posterPath,
                        backdropPath = BACKDROP_URL + it.backdropPath
                    )
                }

                movieList.addAll(newMovies)
                _movies.postValue(movieList)
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