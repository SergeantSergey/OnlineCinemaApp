package com.example.onlinecinemaapp.feature.player.playerClient

import com.google.android.exoplayer2.ui.StyledPlayerView

interface Player {
    fun setView(view: StyledPlayerView)

    fun setUrl(url: String)

    fun play()

    fun pause()

    fun stop()
}