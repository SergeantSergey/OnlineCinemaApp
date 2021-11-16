package com.example.onlinecinemaapp.feature.moviesList.data.api

import com.example.onlinecinemaapp.feature.moviesList.data.module.CinemaModule
import retrofit2.http.GET

interface CinemaApi {

    @GET("eca5141dedc79751b3d0b339649e06a3/raw/38f9419762adf27c34a3f052733b296385b6aa8d/")
    suspend fun getCinema(): CinemaModule
}