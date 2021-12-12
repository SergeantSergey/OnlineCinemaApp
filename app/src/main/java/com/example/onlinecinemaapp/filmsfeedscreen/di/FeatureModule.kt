package com.example.onlinecinemaapp.filmsfeedscreen.di

import com.example.onlinecinemaapp.FILMS_FEED_QUALIFIER
import com.example.onlinecinemaapp.filmsfeedscreen.data.FilmsFeedInteractor
import com.example.onlinecinemaapp.filmsfeedscreen.data.FilmsFeedRepository
import com.example.onlinecinemaapp.filmsfeedscreen.data.FilmsFeedRepositoryImpl
import com.example.onlinecinemaapp.filmsfeedscreen.data.remote.FilmsFeedApi
import com.example.onlinecinemaapp.filmsfeedscreen.data.remote.FilmsFeedRemoteSource
import com.example.onlinecinemaapp.filmsfeedscreen.ui.FilmsFeedScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val filmsFeedScreenModule = module {

    single<FilmsFeedApi> {
        get<Retrofit>().create(FilmsFeedApi::class.java)
    }

    single<FilmsFeedRemoteSource> {
        FilmsFeedRemoteSource(get())
    }

    single<FilmsFeedRepository> {
        FilmsFeedRepositoryImpl(get())
    }

    single<FilmsFeedInteractor> {
        FilmsFeedInteractor(get())
    }

    viewModel<FilmsFeedScreenViewModel> {
        FilmsFeedScreenViewModel(get(), get(named(FILMS_FEED_QUALIFIER)))
    }
}