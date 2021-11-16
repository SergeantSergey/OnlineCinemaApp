package com.example.onlinecinemaapp.feature.moviesList.data.api

import com.example.onlinecinemaapp.feature.moviesList.data.module.CinemaModule

class CinemaRemoteSource(private val api: CinemaApi) {

    suspend fun getCinema(): CinemaModule {
        return api.getCinema()
    }
}