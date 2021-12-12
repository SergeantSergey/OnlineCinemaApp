package com.example.onlinecinemaapp.filmsfeedscreen.data.remote.model

import com.google.gson.annotations.SerializedName

data class FilmsFeedRemoteModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<FilmRemoteModel>
)