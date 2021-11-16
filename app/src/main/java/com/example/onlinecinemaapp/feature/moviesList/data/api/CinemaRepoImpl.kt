package com.example.onlinecinemaapp.feature.moviesList.data.api

import com.example.onlinecinemaapp.feature.moviesList.data.toDomain
import com.example.onlinecinemaapp.feature.moviesList.domain.module.CinemaDomainModule

class CinemaRepoImpl(
    private val remoteSource: CinemaRemoteSource
) : CinemaRepo {

    override suspend fun getCinema(): CinemaDomainModule {
        return remoteSource.getCinema().toDomain()
    }
}