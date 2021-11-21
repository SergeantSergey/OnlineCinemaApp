package com.example.onlinecinemaapp.feature.movieInfo.ui

import com.example.onlinecinemaapp.base.Event

object ViewState

sealed class UiEvent : Event {
    object OnBackPressed : UiEvent()
}