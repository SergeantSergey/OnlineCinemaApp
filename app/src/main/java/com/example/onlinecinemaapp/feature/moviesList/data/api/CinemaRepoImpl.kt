package com.example.onlinecinemaapp.feature.moviesList.data.api

import com.example.onlinecinemaapp.feature.moviesList.domain.module.CinemaDomainModule

class CinemaRepoImpl : CinemaRepo {

    override suspend fun getCinema(cityName: String): CinemaDomainModule {
        return TODO()
    }
}