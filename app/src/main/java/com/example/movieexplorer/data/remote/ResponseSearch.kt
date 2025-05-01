package com.example.movieexplorer.data.remote

import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("Search")
    val search: List<ResponseSearchItem>,
    val totalResults: String,
    @SerializedName("Response")
    val response: String,
    @SerializedName("Error")
    val error: String?
)

