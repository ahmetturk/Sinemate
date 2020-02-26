package com.ahmetroid.popularmovies.detail

import android.content.res.Resources
import androidx.lifecycle.*
import com.ahmetroid.popularmovies.R
import com.ahmetroid.popularmovies.base.BaseViewModel
import com.ahmetroid.popularmovies.data.Repository
import com.ahmetroid.popularmovies.data.model.Movie
import com.ahmetroid.popularmovies.data.model.Video
import com.ahmetroid.popularmovies.util.formatReleaseDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(resources: Resources, movie: Movie, repository: Repository) :
    BaseViewModel() {
    val title = movie.title
    val posterPath = movie.posterPath
    val backdropPath = movie.backdropPath
    val releaseDate = formatReleaseDate(movie.releaseDate)
    val voteAverage = resources.getString(R.string.vote_average_text, movie.voteAverage)
    val overview = movie.overview

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val movieDetail = repository.getMovieDetail(movie.id)
            _videos.postValue(movieDetail.videos.results.map {
                it.copy(image = resources.getString(R.string.youtube_image_url, it.key))
            })
        }
    }

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>>
        get() = _videos
}

class DetailViewModelFactory(
    private val resources: Resources,
    private val movie: Movie,
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DetailViewModel(resources, movie, repository) as T
    }
}