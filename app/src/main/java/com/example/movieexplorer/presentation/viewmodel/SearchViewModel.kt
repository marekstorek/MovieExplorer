package com.example.movieexplorer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorer.data.model.Movie
import com.example.movieexplorer.data.model.MovieSearchResult
import com.example.movieexplorer.data.model.Resource
import com.example.movieexplorer.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val movieRepository: MovieRepository) : ViewModel(){

    private val _searchState = MutableStateFlow<Resource<MovieSearchResult>>(Resource.Success(MovieSearchResult()))
    val searchState: StateFlow<Resource<MovieSearchResult>> = _searchState

    private val _recentMoviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Success(emptyList()))
    val recentMoviesState: StateFlow<Resource<List<Movie>>> = _recentMoviesState
    private var lastQuery = ""
    init {
        getRecentMovies()
    }
    fun searchMovies(query: String = lastQuery, count: Int = 1){
        val callsCount = ((count - 1) / 10) + 1
        viewModelScope.launch {
            movieRepository.searchMovies(query, callsCount).collect{result->
                _searchState.value = result
                lastQuery = query
            }
        }
    }


    fun clearSearchState(){
        _searchState.value = Resource.Success(MovieSearchResult())
    }

    private fun getRecentMovies(){
        viewModelScope.launch {
            movieRepository.getAllMovies().collect{result->
                _recentMoviesState.value = result
            }
        }
    }


}