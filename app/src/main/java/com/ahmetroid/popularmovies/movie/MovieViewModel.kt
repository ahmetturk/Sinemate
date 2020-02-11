package com.ahmetroid.popularmovies.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ahmetroid.popularmovies.base.BaseViewModel
import com.ahmetroid.popularmovies.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel : BaseViewModel() {

    // TODO Get repository by constructor
    private val repository = Repository()

    private val _movie = MutableLiveData<String>()
    val movie: LiveData<String>
        get() = _movie

    fun getMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            val movie = repository.getMovies().getOrNull(0)
            _movie.postValue(movie?.title ?: "Exception")
        }
    }

}