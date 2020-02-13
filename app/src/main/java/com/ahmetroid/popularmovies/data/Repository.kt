package com.ahmetroid.popularmovies.data

import com.ahmetroid.popularmovies.data.model.Movie
import com.ahmetroid.popularmovies.data.network.Api.apiService
import retrofit2.HttpException

// TODO Get api service by constructor
class Repository {

    // TODO use sealed class Resource for exposing Success, Loading, Error states
    suspend fun getMovies(page: Int): List<Movie> {
        return try {
            // TODO Get language from phone's language
            apiService.getPopularMovies("tr-TR", page.toString()).results
        } catch (e: HttpException) {
            return emptyList()
        }
    }

}