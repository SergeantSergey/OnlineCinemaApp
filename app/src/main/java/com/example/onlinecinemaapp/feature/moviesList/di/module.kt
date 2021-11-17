package com.example.onlinecinemaapp.feature.moviesList.di

import com.example.onlinecinemaapp.feature.moviesList.data.api.CinemaApi
import com.example.onlinecinemaapp.feature.moviesList.data.api.CinemaRemoteSource
import com.example.onlinecinemaapp.feature.moviesList.data.api.CinemaRepo
import com.example.onlinecinemaapp.feature.moviesList.data.api.CinemaRepoImpl
import com.example.onlinecinemaapp.feature.moviesList.domain.MoviesListInteractor
import com.example.onlinecinemaapp.feature.moviesList.ui.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val moviesListModule = module {

    single {
        get<Retrofit>().create(CinemaApi::class.java)
    }

    single {
        CinemaRemoteSource(api = get())
    }

    single<CinemaRepo> {
        CinemaRepoImpl(remoteSource = get())
    }

    single {
        MoviesListInteractor(cinemaRepo = get())
    }

    viewModel {
        MoviesListViewModel(moviesListInteractor = get())
    }
}