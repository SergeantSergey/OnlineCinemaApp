package com.example.onlinecinemaapp.filmsfeedscreen.data

import com.example.onlinecinemaapp.filmsfeedscreen.data.remote.model.FilmRemoteModel
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.GenresModel

fun List<FilmRemoteModel>.mapToUiModel(): List<FilmModel> {
    return map {
        FilmModel(
            id = it.id,
            title = it.title,
            genres = it.genres.map {
                GenresModel(
                    it.name
                )
            },
            voteAverage = it.voteAverage,
            voteCount = it.voteCount,
            posterPath = it.posterPath,
            video = it.video,
            adult = it.adult,
            overview = it.overview
        )
    }
}