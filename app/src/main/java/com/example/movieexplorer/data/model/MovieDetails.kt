package com.example.movieexplorer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movieDetails")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val imdbId: String,
    val title: String,
    val type: MovieType,
    @SerializedName("year_from")
    val yearFrom: Int,
    @SerializedName("year_to")
    val yearTo: Int?,
    val poster: String,
    val genre: List<String>,
    val directors: List<String>,
    val writers: List<String>,
    val actors: List<String>,
    val plot: String,
    val language: List<String>,
    val country: List<String>,
    @SerializedName("rating")
    val imdbRating: Double?,
    @SerializedName("")
    var seenAtInMillis: Long,
    var isFavourite: Boolean = false,
    //val isOnWatchList: Boolean = false
)

fun Movie.toMoviePreview() : MovieSummary{
    return MovieSummary(
        title, yearFrom, imdbId, type, poster
    )
}




