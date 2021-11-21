package com.example.onlinecinemaapp.feature.player.domain

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

class PlayerInteractor(
    private val player: ExoPlayer
) {

    fun setView(view: StyledPlayerView) {
        view.player = player
    }

    fun setUrl(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
    }

    fun playerPlay() {
        player.prepare()
        player.play()
    }

    fun playerPause() {
        player.pause()
    }

    fun stopPlayer() {
        player.stop()
    }
}