package com.example.onlinecinemaapp.filmsdescriptionscreen.ui

import com.example.onlinecinemaapp.base.Event
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel

data class ViewState(
    val filmsFeedModel: FilmModel? = null
)

sealed class UiEvent : Event {
    data class ShowFilmDescription(val filmsFeedModel: FilmModel) : UiEvent()
    data class OnConnectionChanged(val isConnectionAvailable: Boolean) : UiEvent()
    object OnBackPressed : UiEvent()
}