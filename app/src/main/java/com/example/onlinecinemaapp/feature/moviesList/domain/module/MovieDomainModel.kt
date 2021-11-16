package com.example.onlinecinemaapp.feature.moviesList.domain.module

data class MovieDomainModel(

    val adult: Boolean,

    val genres: List<GenresDomainModel>,

    val id: Int,

    val originalLanguage: String,

    val originalTitle: String,

    val overview: String,

    val releaseDate: String,

    val posterPath: String,

    val popularity: Float,

    val title: String,

    val video: String,

    val voteAverage: String,

    val voteCount: String,
)