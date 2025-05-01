package com.example.movieexplorer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorer.data.model.Movie
import com.example.movieexplorer.data.model.Resource
import com.example.movieexplorer.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _movieDetailsState = MutableStateFlow<Resource<Movie>>(Resource.Loading())
    val movieDetailsState: StateFlow<Resource<Movie>> = _movieDetailsState

    fun getMovieById(id: String){
        viewModelScope.launch {
            _movieDetailsState.value = Resource.Loading()
            _movieDetailsState.value = movieRepository.getMovieById(id)
        }
    }

    fun toggleMovieIsFavourite(movie: Movie) {
        viewModelScope.launch {
            val updatedMovie = movie.copy(isFavourite = !movie.isFavourite)
            movieRepository.updateMovie(updatedMovie)
            _movieDetailsState.value = Resource.Success(updatedMovie)
        }
    }
}