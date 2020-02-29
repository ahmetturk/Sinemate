package com.ahmetroid.popularmovies.data

import androidx.lifecycle.LiveData
import com.ahmetroid.popularmovies.data.model.Movie
import com.ahmetroid.popularmovies.data.model.MovieDetail
import com.ahmetroid.popularmovies.db.AppDatabase
import com.ahmetroid.popularmovies.network.Api

class Repository(
    private val api: Api,
    private val appDatabase: AppDatabase
) {

    // TODO use sealed class Resource for exposing Success, Loading, Error states
    suspend fun loadMovies(page: Int): List<Movie> {
        return try {
            api.service.getPopularMovies(api.language, page.toString()).results
        } catch (e: Exception) {
            return emptyList()
        }
    }

    fun getMovies(): LiveData<List<Movie>> = appDatabase.moviesDao().getAll()

    fun insertMoviesToDb(movies: List<Movie>) {
        appDatabase.moviesDao().insertAll(movies)
    }

    suspend fun getMovieDetail(id: String): MovieDetail {
        return api.service.getMovieById(id)
    }

}