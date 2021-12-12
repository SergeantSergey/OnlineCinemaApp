package com.example.onlinecinemaapp.filmsfeedscreen.data

import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel

interface FilmsFeedRepository {
    suspend fun getFilmsFeed(): List<FilmModel>
}