package com.ahmetroid.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetroid.popularmovies.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseViewModel : ViewModel() {

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