package com.example.onlinecinemaapp.feature.player.domain

import com.example.onlinecinemaapp.feature.player.playerClient.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

class PlayerInteractor(
    private val player: Player
) {

    fun setView(view: StyledPlayerView) {
        player.setView(view)
    }

    fun setUrl(url: String) {
        player.setUrl(url)
    }

    fun playerPlay() {
        player.play()
    }

    fun playerPause() {
        player.pause()
    }

    fun stopPlayer() {
        player.stop()
    }
}