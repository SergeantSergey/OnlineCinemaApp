package com.example.onlinecinemaapp.filmsfeedscreen.data

import com.example.onlinecinemaapp.extensions.Either
import com.example.onlinecinemaapp.extensions.attempt
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel

class FilmsFeedInteractor(private val filmsFeedRepository: FilmsFeedRepository) {
    suspend fun getFilmsFeed(): Either<Throwable, List<FilmModel>> {
        return attempt { filmsFeedRepository.getFilmsFeed() }
    }
}