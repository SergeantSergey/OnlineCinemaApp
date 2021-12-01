package com.example.onlinecinemaapp.feature.player.di

import com.example.onlinecinemaapp.feature.player.domain.PlayerInteractor
import com.example.onlinecinemaapp.feature.player.playerClient.Player
import com.example.onlinecinemaapp.feature.player.playerClient.PlayerImpl
import com.example.onlinecinemaapp.feature.player.ui.PlayerViewModel
import com.google.android.exoplayer2.ExoPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playerModule = module {

    single {
        ExoPlayer.Builder(androidContext()).build()
    }

    single<Player> {
        PlayerImpl(exoPlayer = get())
    }

    single {
        PlayerInteractor(player = get())
    }

    viewModel { (url: String) ->
        PlayerViewModel(url = url, playerInteractor = get())
    }
}