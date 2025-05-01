package com.example.movieexplorer.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.movieexplorer.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("SELECT * FROM movieDetails WHERE imdbId = :id")
    fun getMovie(id: String): Flow<Movie?>

    @Query("SELECT * FROM movieDetails WHERE imdbId = :id")
    suspend fun getMovieOnce(id: String): Movie?

    @Query("DELETE FROM movieDetails WHERE imdbId = :id")
    suspend fun deleteMovie(id: String)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM movieDetails ORDER BY seenAtInMillis DESC")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movieDetails ORDER BY imdbRating DESC")
    fun getAllMoviesByRating(): Flow<List<Movie>>

    @Query("SELECT * FROM movieDetails WHERE isFavourite = 1")
    fun getFavouriteMovies(): Flow<List<Movie>>

    @Update
    suspend fun updateMovie(movie: Movie)

}