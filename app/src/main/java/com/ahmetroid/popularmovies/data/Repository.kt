package com.ahmetroid.popularmovies.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ahmetroid.popularmovies.data.model.Movie
import com.ahmetroid.popularmovies.data.model.MovieDetail
import com.ahmetroid.popularmovies.db.AppDatabase
import com.ahmetroid.popularmovies.network.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val POSTER_URL = "https://image.tmdb.org/t/p/w342"
private const val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

class Repository(
    private val api: Api,
    private val appDatabase: AppDatabase
) {

    // TODO use sealed class Resource for exposing Success, Loading, Error states
    private suspend fun fetchMovies(page: Int): List<Movie> {
        return try {
            val response = api.service.getPopularMovies(api.language, page.toString())
            response.results.map {
                it.copy(
                    posterPath = POSTER_URL + it.posterPath,
                    backdropPath = BACKDROP_URL + it.backdropPath,
                    page = response.page
                )
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }

    fun getMovies(scope: CoroutineScope): LiveData<PagedList<Movie>> =
        appDatabase.moviesDao().getAll().toLiveData(
            pageSize = 20, boundaryCallback = MoviesBoundaryCallback(this, scope)
        )

    fun loadMovies(scope: CoroutineScope, page: Int) {
        scope.launch(Dispatchers.IO) {
            val movies = fetchMovies(page)
            appDatabase.moviesDao().insertAll(movies)
        }
    }

    suspend fun getMovieDetail(id: String): MovieDetail {
        return api.service.getMovieById(id)
    }

}