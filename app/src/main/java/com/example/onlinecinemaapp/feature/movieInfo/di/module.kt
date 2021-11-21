package com.example.onlinecinemaapp.feature.movieInfo.di

import com.example.onlinecinemaapp.feature.movieInfo.ui.MovieInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieInfoModule = module {

    viewModel {
        MovieInfoViewModel(router = get())
    }
}