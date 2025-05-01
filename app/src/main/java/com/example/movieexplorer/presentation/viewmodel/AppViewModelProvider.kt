package com.example.movieexplorer.presentation.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movieexplorer.MovieApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                movieApplication().container.movieRepository
            )
        }
        initializer {
            MovieDetailsViewModel(
                movieApplication().container.movieRepository
            )
        }
        initializer {
            SearchViewModel(
                movieApplication().container.movieRepository
            )
        }
    }
}

fun CreationExtras.movieApplication(): MovieApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApplication)