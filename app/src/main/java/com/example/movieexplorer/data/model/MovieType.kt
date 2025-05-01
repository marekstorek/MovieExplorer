package com.example.movieexplorer.data.model

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName

enum class MovieType {
    @SerializedName("movie") MOVIE,
    @SerializedName("series") SERIES,
    @SerializedName("episode") EPISODE;

    companion object {
        fun getMovieType(movieType: String): MovieType {
            return when(movieType) {
                "series" -> SERIES
                "episode" -> EPISODE
                else -> MOVIE
            }
        }
    }
    fun movieTypeColor() : Color {
        return when(this) {
            MOVIE-> Color(0xff64B5F6)//Color.Companion.Blue
            SERIES -> Color(0xffFFB74D)//Color.Companion.Red
            EPISODE -> Color(0xff81C784)//Color.Companion.Gray
        }
    }

    override fun toString(): String {
        return when(this) {
            MOVIE-> "Movie"
            SERIES -> "Series"
            EPISODE -> "Episode"
        }
        return super.toString()
    }
}