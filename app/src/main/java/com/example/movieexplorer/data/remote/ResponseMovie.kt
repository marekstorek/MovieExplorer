package com.example.movieexplorer.data.remote

import com.example.movieexplorer.data.model.Movie
import com.example.movieexplorer.data.model.MovieType
import com.google.gson.annotations.SerializedName

class ResponseMovie(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Director")
    val directors: String,
    @SerializedName("Writer")
    val writer: String,
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Language")
    val language: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("imdbRating")
    val imdbRating: String,
    @SerializedName("imdbID")
    val imdbId: String,
    @SerializedName("Type")
    val type: String,
) {
    fun toMovie() : Movie {
        return Movie(
            imdbId = imdbId,
            title = title,
            type = getMovieType(),
            yearFrom = getYearFrom(),
            yearTo = getYearTo(),
            poster = poster,
            genre = genre.toList(),
            directors = directors.toList(),
            writers = writer.toList(),
            actors = actors.toList(),
            plot = plot,
            language = language.toList(),
            country = country.toList(),
            imdbRating = imdbRating.toDoubleOrNull(),
            seenAtInMillis = System.currentTimeMillis(),
            isFavourite = false,
        )
    }

    private fun getMovieType() : MovieType {
        return MovieType.Companion.getMovieType(type)
    }

    private fun getYearFrom() : Int{
        return year.split("–").firstOrNull()?.toInt() ?: 2000
    }

    private fun getYearTo() : Int?{
        return year.split("–").getOrNull(1)?.toIntOrNull()
    }

    private fun String.toList() : List<String>{
        return this.split(",").map { it.trim() }
    }

}
