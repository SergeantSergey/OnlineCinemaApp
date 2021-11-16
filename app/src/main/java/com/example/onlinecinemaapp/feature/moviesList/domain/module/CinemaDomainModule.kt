package com.example.onlinecinemaapp.feature.moviesList.domain.module

data class CinemaDomainModule(
    val page: Int,

    val results: List<MovieDomainModel>,

    val totalPages: Int,

    val totalResults: Int,
)