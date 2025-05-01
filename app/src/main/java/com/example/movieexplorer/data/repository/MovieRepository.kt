package com.example.movieexplorer.data.repository

import android.util.Log
import com.example.movieexplorer.data.local.MovieDao
import com.example.movieexplorer.data.model.Movie
import com.example.movieexplorer.data.model.MovieSearchResult
import com.example.movieexplorer.data.model.MovieSummary
import com.example.movieexplorer.data.model.Resource
import com.example.movieexplorer.data.remote.MovieApiService
import com.example.movieexplorer.data.remote.toMovieSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.math.min

class MovieRepository(private val movieDao: MovieDao, private val movieApiService: MovieApiService) {

    fun searchMovies(query: String, calls: Int = 1) : Flow<Resource<MovieSearchResult>> = flow {
        if (query.length < 2) {
            emit(Resource.Success(MovieSearchResult()))
            return@flow
        }
        emit(Resource.Loading())
        try {
            val moviesList: MutableList<MovieSummary> = mutableListOf()
            var responseCount = 0
            for (i in 1..calls){
                val responseSearch = movieApiService.searchMovies(search = query, page = i)
                if (responseSearch.response == "False") {
                    emit(Resource.Error(responseSearch.error ?: "Something went wrong"))
                    return@flow
                }
                moviesList.addAll(responseSearch.search.map { it.toMovieSummary() })
                responseCount = min(responseSearch.totalResults.toIntOrNull() ?: 0, 100)
            }
            val movieSearchResult = MovieSearchResult(moviesList, responseCount)
            emit(Resource.Success(movieSearchResult))
        } catch (e: Exception) {
            Log.e("HTTP EXCEPTION", "Couldnt search from internet: $e")
            emit(Resource.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)





    fun getAllMovies() : Flow<Resource<List<Movie>>>  {
        return movieDao.getAllMovies()
            .map { movies ->
                if (movies.isEmpty()) {
                    Resource.Error("No movies found")
                } else {
                    Resource.Success(movies)
                }
            }
            .onStart {
                emit(Resource.Loading()) // Emit loading state when starting
            }


    }
    fun getFavoriteMovies() : Flow<Resource<List<Movie>>> {
        return movieDao.getFavouriteMovies()
            .map { movies ->
                if (movies.isEmpty()) {
                    Resource.Error("No movies found")
                } else {
                    Resource.Success(movies)
                }
            }
            .onStart {
                emit(Resource.Loading()) // Emit loading state when starting
            }
    }

    fun getMoviesByRating() : Flow<Resource<List<Movie>>> {
        return movieDao.getAllMoviesByRating()
            .map { movies ->
                if (movies.isEmpty()) {
                    Resource.Error("No movies found")
                } else {
                    Resource.Success(movies)
                }
            }
            .onStart {
                emit(Resource.Loading()) // Emit loading state when starting
            }
    }

    suspend fun getMovieById(id: String) : Resource<Movie> {
        var movie = movieDao.getMovieOnce(id)
        return if(movie == null) {
            try {
                movie = movieApiService.getById(id).toMovie()
                movie.seenAtInMillis = System.currentTimeMillis()
                movieDao.insertMovie(movie)
                Resource.Success(movie)
            } catch (e: Exception) {
                Resource.Error("Couldnt load movie: ${e.message}")
            }
        } else {
            movie = movie.copy(seenAtInMillis = System.currentTimeMillis())
            movieDao.updateMovie(movie)
            Resource.Success(movie)
        }
    }

//    suspend fun saveMovie(movie: Movie) {
//        movieDao.insertMovie(movie)
//    }

    suspend fun updateMovie(movie: Movie) {
        movieDao.updateMovie(movie)
    }

//    suspend fun deleteMovie(movie: Movie) {
//        movieDao.deleteMovie(movie)
//    }
//
//    suspend fun deleteMovie(id: String) {
//        movieDao.deleteMovie(id)
//    }

}