package com.ahmetroid.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmetroid.popularmovies.data.Repository

class BaseViewModel : ViewModel() {

    // TODO Get repository by constructor
    private val repository = Repository()

    private val _movie = MutableLiveData<String>()
    val movie: LiveData<String>
        get() = _movie

    fun getMovie() {
        _movie.value = repository.getMovie()
    }
}