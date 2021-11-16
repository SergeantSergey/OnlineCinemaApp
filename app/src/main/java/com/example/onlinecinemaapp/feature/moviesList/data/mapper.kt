package com.example.onlinecinemaapp.feature.moviesList.data

import com.example.onlinecinemaapp.feature.moviesList.data.module.CinemaModule
import com.example.onlinecinemaapp.feature.moviesList.data.module.GenresModel
import com.example.onlinecinemaapp.feature.moviesList.data.module.MovieModel
import com.example.onlinecinemaapp.feature.moviesList.domain.module.CinemaDomainModule
import com.example.onlinecinemaapp.feature.moviesList.domain.module.GenresDomainModel
import com.example.onlinecinemaapp.feature.moviesList.domain.module.MovieDomainModel

fun GenresModel.toDomain(): GenresDomainModel {
    return GenresDomainModel(
        name = this.name
    )
}

fun MovieModel.toDomain(): MovieDomainModel {
    return MovieDomainModel(
        adult = this.adult,
        genres = this.genres.map { genresModel ->
            genresModel.toDomain()
        },
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        releaseDate = this.releaseDate,
        posterPath = this.posterPath,
        popularity = this.popularity,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

fun CinemaModule.toDomain(): CinemaDomainModule {
    return CinemaDomainModule(
        page = this.page,
        results = this.results.map { movieModel ->
            movieModel.toDomain()
        },
        totalPages = this.totalPages,
        totalResults = this.totalResults
    )
}
