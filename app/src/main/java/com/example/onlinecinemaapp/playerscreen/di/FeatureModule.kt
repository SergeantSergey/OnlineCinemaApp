package com.example.onlinecinemaapp.playerscreen.di

import com.example.onlinecinemaapp.player.VideoPlayer
import com.example.onlinecinemaapp.player.VideoPlayerImpl
import com.example.onlinecinemaapp.playerscreen.PlayerScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val videoPlayerModule = module {

    single<VideoPlayer> {
        VideoPlayerImpl(androidContext())
    }

    viewModel {
        PlayerScreenViewModel(get())
    }
}