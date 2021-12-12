package com.example.onlinecinemaapp.playerscreen

import android.view.View
import com.example.onlinecinemaapp.base.Event

data class ViewState(val status: Status, val currentTitle: String, val errorType: Int)

sealed class UiEvent : Event {
    data class OnConnectionChanged(val isConnectionAvailable: Boolean) : UiEvent()
    data class OnPlayerPlay(val videoId: Int, val title: String, val url: String) : UiEvent()
    data class OnPlayerPlaylist(val urlList: List<Pair<String, String>>) : UiEvent()
    data class OnPlayerInit(val view: View) : UiEvent()
    data class PlayerTitleChanged(val title: String) : UiEvent()
    object OnVideoPlayerPause : UiEvent()
    object OnPlayerBuffered : UiEvent()
    object OnPlayerBufferedEnded : UiEvent()
}

sealed class ErrorEvent : Event {
    data class OnPlayerError(val error: Int) : ErrorEvent()
}

enum class Status {
    PLAYER_INIT,
    PLAYER_BUFFERED,
    PLAYER_BUFFERED_ENDED,
    PLAYER_ERROR
}