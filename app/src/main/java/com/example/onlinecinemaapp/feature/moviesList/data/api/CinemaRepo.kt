package com.example.onlinecinemaapp.feature.moviesList.data.api

import com.example.onlinecinemaapp.feature.moviesList.domain.module.CinemaDomainModule

interface CinemaRepo {

    suspend fun getCinema(): CinemaDomainModule
}