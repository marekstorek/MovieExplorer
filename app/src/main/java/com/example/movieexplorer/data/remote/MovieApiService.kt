package com.example.movieexplorer.data.remote

import com.example.movieexplorer.data.model.MovieType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY // Log everything, including body (use Level.BASIC for less info)
}

// Create OkHttpClient and add the interceptor
private val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

val movieApiService: MovieApiService = Retrofit
    .Builder()
    .baseUrl("https://www.omdbapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
    .create(MovieApiService::class.java)

interface MovieApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") search: String,
        @Query("y") year: Int? = null,
        @Query("page") page: Int? = null,
        @Query("type") type: MovieType? = null,
        @Query("apikey") apiKey: String = API_KEY
    ) : ResponseSearch

    @GET("/")
    suspend fun getById(
        @Query("i") id: String,
        @Query("y") year: Int? = null,
        @Query("type") type: MovieType? = null,
        @Query("plot") plotLength: PlotLength? = PlotLength.FULL,
        @Query("apikey") apiKey: String = API_KEY
    ) : ResponseMovie

//    @GET("/")
//    suspend fun getByName(
//        @Query("t") id: String,
//        @Query("y") year: Int? = null,
//        @Query("type") type: MovieType? = null,
//        @Query("plot") plotLength: PlotLength? = null,
//        @Query("apikey") apiKey: String = API_KEY
//    ) : ResponseDetails
}