package com.example.onlinecinemaapp.feature.moviesList.domain

import com.example.onlinecinemaapp.feature.moviesList.data.api.CinemaRepo
import com.example.onlinecinemaapp.feature.moviesList.domain.module.CinemaDomainModule

class MoviesListInteractor(
    private val cinemaRepo: CinemaRepo
) {

    suspend fun getCinema(): CinemaDomainModule {
        return cinemaRepo.getCinema()
    }
}