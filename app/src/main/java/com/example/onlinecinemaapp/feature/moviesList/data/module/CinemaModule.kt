package com.example.onlinecinemaapp.feature.moviesList.data.module

import com.google.gson.annotations.SerializedName

data class CinemaModule(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieModel>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int,
)