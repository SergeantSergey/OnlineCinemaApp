package com.example.onlinecinemaapp.filmsdescriptionscreen.di

import com.example.onlinecinemaapp.FILMS_FEED_QUALIFIER
import com.example.onlinecinemaapp.filmsdescriptionscreen.ui.FilmDescriptionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val filmDescriptionModule = module {
    viewModel {
        FilmDescriptionViewModel(get(named(FILMS_FEED_QUALIFIER)))
    }
}