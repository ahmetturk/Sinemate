package com.ahmetroid.popularmovies.data

import com.ahmetroid.popularmovies.data.model.Movie
import com.ahmetroid.popularmovies.data.model.MovieDetail
import com.ahmetroid.popularmovies.data.network.ApiService
import retrofit2.HttpException

class Repository(
    private val apiService: ApiService,
    private val language: String
) {

    // TODO use sealed class Resource for exposing Success, Loading, Error states
    suspend fun getMovies(page: Int): List<Movie> {
        return try {
            apiService.getPopularMovies(language, page.toString()).results
        } catch (e: HttpException) {
            return emptyList()
        }
    }

    suspend fun getMovieDetail(id: String): MovieDetail {
        return apiService.getMovieById(id)
    }

}