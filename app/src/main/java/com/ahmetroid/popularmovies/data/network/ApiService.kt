package com.ahmetroid.popularmovies.data.network

import com.ahmetroid.popularmovies.data.model.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: String
    ): ApiResponse<Movie>

}