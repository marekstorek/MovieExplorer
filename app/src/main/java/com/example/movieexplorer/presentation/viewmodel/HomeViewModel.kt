package com.example.movieexplorer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorer.data.model.Movie
import com.example.movieexplorer.data.model.Resource
import com.example.movieexplorer.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel(){

    private val _recentMoviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Success(emptyList()))
    val recentMoviesState: StateFlow<Resource<List<Movie>>> = _recentMoviesState


    private val _favouriteMoviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Success(emptyList()))
    val favouriteMoviesState: StateFlow<Resource<List<Movie>>> = _favouriteMoviesState


    private val _watchlistMoviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Success(emptyList()))
    val watchlistMoviesState: StateFlow<Resource<List<Movie>>> = _watchlistMoviesState


    private val _moviesByRatingState = MutableStateFlow<Resource<List<Movie>>>(Resource.Success(emptyList()))
    val moviesByRatingState: StateFlow<Resource<List<Movie>>> = _moviesByRatingState


    private fun getRecentMovies(){
        viewModelScope.launch {
            movieRepository.getAllMovies().collect{result->
                _recentMoviesState.value = result
            }
        }
    }

    private fun getFavouriteMovies(){
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().collect {result->
                _favouriteMoviesState.value = result
            }
        }
    }

    private fun getMoviesByRating(){
        viewModelScope.launch {
            movieRepository.getMoviesByRating().collect {result->
                _moviesByRatingState.value = result
            }
        }
    }


    init {
        getRecentMovies()
        getFavouriteMovies()
        getMoviesByRating()
    }
}