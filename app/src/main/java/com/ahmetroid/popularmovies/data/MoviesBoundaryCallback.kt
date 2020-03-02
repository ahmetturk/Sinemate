package com.ahmetroid.popularmovies.data

import androidx.paging.PagedList
import com.ahmetroid.popularmovies.data.model.Movie
import kotlinx.coroutines.CoroutineScope

class MoviesBoundaryCallback(
    private val repository: Repository,
    private val scope: CoroutineScope
) :
    PagedList.BoundaryCallback<Movie>() {
    override fun onZeroItemsLoaded() {
        repository.loadMovies(scope, 1)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        repository.loadMovies(scope, itemAtEnd.page + 1)
    }
}