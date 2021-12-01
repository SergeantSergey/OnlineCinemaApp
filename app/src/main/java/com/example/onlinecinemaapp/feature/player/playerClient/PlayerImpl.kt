package com.example.onlinecinemaapp.feature.player.playerClient

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

class PlayerImpl(
    private val exoPlayer: ExoPlayer
) : Player {

    override fun setView(view: StyledPlayerView) {
        view.player = exoPlayer
    }

    override fun setUrl(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
    }

    override fun play() {
        exoPlayer.prepare()
        exoPlayer.play()
    }

    override fun pause() {
        exoPlayer.pause()
    }

    override fun stop() {
        exoPlayer.stop()
        exoPlayer.release()
    }
}