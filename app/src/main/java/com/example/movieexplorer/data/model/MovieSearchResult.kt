package com.example.movieexplorer.data.model

data class MovieSearchResult(
    val movies: List<MovieSummary> = emptyList(),
    val totalResults: Int = 0,
)