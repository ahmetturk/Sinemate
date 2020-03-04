package com.ahmetroid.popularmovies.data

import androidx.paging.DataSource
import com.ahmetroid.popularmovies.data.model.Movie
import com.ahmetroid.popularmovies.data.model.MovieDetail
import com.ahmetroid.popularmovies.db.AppDatabase
import com.ahmetroid.popularmovies.network.Api

private const val POSTER_URL = "https://image.tmdb.org/t/p/w342"
private const val BACKDROP_URL = "https://image.tmdb.org/t/p/w780"

class Repository(
    private val api: Api,
    private val appDatabase: AppDatabase
) {
    suspend fun fetchMovies(page: Int): Resource<List<Movie>> {
        return try {
            val response = api.service.getPopularMovies(api.language, page.toString())
            val movies = response.results.map {
                it.copy(
                    posterPath = POSTER_URL + it.posterPath,
                    backdropPath = BACKDROP_URL + it.backdropPath,
                    page = response.page
                )
            }
            Resource.Success(movies)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    fun getMovies(): DataSource.Factory<Int, Movie> =
        appDatabase.moviesDao().getAll()

    suspend fun insertMovies(movies: List<Movie>) {
        appDatabase.moviesDao().insertAll(movies)
    }

    suspend fun deleteMovies() {
        appDatabase.moviesDao().deleteAll()
    }

    suspend fun getMovieDetail(id: String): MovieDetail {
        return api.service.getMovieById(id)
    }

}