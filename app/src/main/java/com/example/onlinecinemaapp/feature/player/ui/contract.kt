package com.example.onlinecinemaapp.feature.player.ui

import com.example.onlinecinemaapp.base.Event
import com.google.android.exoplayer2.ui.StyledPlayerView

object ViewState

sealed class UiEvent : Event {

    data class SetPlayerView(val view: StyledPlayerView) : UiEvent()
    data class SetVideoUrl(val url: String) : UiEvent()
    object PlayVideo : UiEvent()
    object PauseVide : UiEvent()
    object StopVideo : UiEvent()

}