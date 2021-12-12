package com.example.onlinecinemaapp.player

import android.view.View

interface VideoPlayer {
    fun setView(view: View)
    fun play(id: Int, title: String, url: String)
    fun playPlaylist(urlList: List<Pair<String, String>>)
    fun pause()
    fun setPlayerListener(
        onLoad: () -> Unit,
        onLoadEnded: () -> Unit,
        onTitleChanged: (String) -> Unit,
        onError: (Int) -> Unit
    )
    fun release()
}