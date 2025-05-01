package com.example.movieexplorer

import android.content.Context
import com.example.movieexplorer.data.local.MovieDatabase
import com.example.movieexplorer.data.remote.movieApiService
import com.example.movieexplorer.data.repository.MovieRepository

class AppContainer(private val context: Context) {
     val movieRepository: MovieRepository by lazy {
         MovieRepository(MovieDatabase.getDatabase(context).movieDao(), movieApiService)
    }
}

