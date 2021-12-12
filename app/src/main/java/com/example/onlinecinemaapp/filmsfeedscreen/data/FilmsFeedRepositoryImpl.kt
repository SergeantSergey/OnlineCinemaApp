package com.example.onlinecinemaapp.filmsfeedscreen.data

import com.example.onlinecinemaapp.filmsfeedscreen.data.remote.FilmsFeedRemoteSource
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel

class FilmsFeedRepositoryImpl(private val filmsFeedRemoteSource: FilmsFeedRemoteSource) :
    FilmsFeedRepository {
    override suspend fun getFilmsFeed(): List<FilmModel> {
        return filmsFeedRemoteSource.getFilmsFeed().results.mapToUiModel()
    }
}