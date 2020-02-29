package com.ahmetroid.popularmovies.network

import com.ahmetroid.popularmovies.data.model.MovieDetail
import com.ahmetroid.popularmovies.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: String
    ): MoviesResponse

    @GET("movie/{id}?append_to_response=videos")
    suspend fun getMovieById(@Path("id") id: String): MovieDetail

}