package com.example.onlinecinemaapp.feature.moviesList

import com.example.onlinecinemaapp.feature.moviesList.data.api.CinemaApi
import org.koin.dsl.module
import retrofit2.Retrofit

val moviesListModule = module {

    single {
        get<Retrofit>().create(CinemaApi::class.java)
    }
}