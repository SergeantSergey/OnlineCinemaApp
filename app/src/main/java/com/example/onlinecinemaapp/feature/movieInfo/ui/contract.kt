package com.example.onlinecinemaapp.feature.movieInfo.ui

import com.example.onlinecinemaapp.base.Event

object ViewState

sealed class UiEvent : Event {
    data class OnPlayClicked(val ulr: String) : UiEvent()
}