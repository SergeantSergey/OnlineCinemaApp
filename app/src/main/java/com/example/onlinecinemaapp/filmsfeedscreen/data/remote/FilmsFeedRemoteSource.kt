package com.example.onlinecinemaapp.filmsfeedscreen.data.remote

import com.example.onlinecinemaapp.filmsfeedscreen.data.remote.model.FilmsFeedRemoteModel

class FilmsFeedRemoteSource(private val filmsFeedApi: FilmsFeedApi) {
    suspend fun getFilmsFeed(): FilmsFeedRemoteModel {
        return filmsFeedApi.getFilmsFeed()
    }
}