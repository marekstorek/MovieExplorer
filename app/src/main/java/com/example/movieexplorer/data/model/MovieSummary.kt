package com.example.movieexplorer.data.model

data class MovieSummary(
    val title: String,
    val year: Int,
    val imdbId: String,
    val type: MovieType,
    val poster: String,
)
