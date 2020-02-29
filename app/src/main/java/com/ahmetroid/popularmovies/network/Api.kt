package com.ahmetroid.popularmovies.network

import com.ahmetroid.popularmovies.BuildConfig.TMDB_API_KEY
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val QUERY_PARAMETER_API = "api_key"

private val moshi = Moshi.Builder().build()

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val httpUrl = chain.request().url().newBuilder()
            .addQueryParameter(QUERY_PARAMETER_API, TMDB_API_KEY)
            .build()

        val request = chain.request().newBuilder()
            .url(httpUrl)
            .build()

        return@addInterceptor chain.proceed(request)
    }
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()

object Api {
    lateinit var language: String
    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }
}