package com.ahmetroid.popularmovies.data

import com.ahmetroid.popularmovies.data.model.Movie
import com.ahmetroid.popularmovies.data.network.Api.apiService
import retrofit2.HttpException

class Repository {

    suspend fun getMovies(): List<Movie> {
        return try {
            apiService.getPopularMovies("tr-TR", "1").results
        } catch (e: HttpException) {
            return emptyList()
        }
    }

}