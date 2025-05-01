package com.example.movieexplorer.data.remote

import com.example.movieexplorer.data.model.MovieSummary
import com.example.movieexplorer.data.model.MovieType
import com.google.gson.annotations.SerializedName

data class ResponseSearchItem(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("imdbID")
    val imdbId: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val poster: String,
)
fun ResponseSearchItem.toMovieSummary() : MovieSummary {
    return MovieSummary(
        title, year.split("–").first().toInt(), imdbId, MovieType.getMovieType(type), poster
    )
}
