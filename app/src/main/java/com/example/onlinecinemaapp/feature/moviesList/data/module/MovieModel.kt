package com.example.onlinecinemaapp.feature.moviesList.data.module

import com.google.gson.annotations.SerializedName

data class MovieModel(

    // взрослый
    @SerializedName("adult")
    val adult: Boolean,

    // жанры
    @SerializedName("genres")
    val genres: List<GenresModel>,

    // идентификатор
    @SerializedName("id")
    val id: Int,

    // исходный язык
    @SerializedName("original_language")
    val originalLanguage: String,

    // исходное название
    @SerializedName("original_title")
    val originalTitle: String,

    // обзор (описание)
    @SerializedName("overview")
    val overview: String,

    // дата выпуска
    @SerializedName("release_date")
    val releaseDate: String,

    // ссылка на изображение
    @SerializedName("poster_path")
    val posterPath: String,

    // популярность
    @SerializedName("popularity")
    val popularity: Float,

    // название
    @SerializedName("title")
    val title: String,

    // ссылка на видео
    @SerializedName("video")
    val video: String,

    // средняя оценка
    @SerializedName("vote_average")
    val voteAverage: String,

    // колличество оценок
    @SerializedName("vote_count")
    val voteCount: String,
)