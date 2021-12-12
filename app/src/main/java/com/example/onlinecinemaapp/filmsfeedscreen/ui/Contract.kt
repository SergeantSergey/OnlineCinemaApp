package com.example.onlinecinemaapp.filmsfeedscreen.ui

import com.example.onlinecinemaapp.base.Event
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmSelectableModel

data class ViewState(
    val state: State
)

sealed class UiEvent : Event {
    data class OnItemClick(val model: FilmModel) : UiEvent()
    object OnClickRepeat : UiEvent()
    data class OnConnectionChanged(val isConnectionAvailable: Boolean) : UiEvent()
    data class OnEnableSelectedMode(val model: FilmModel) : UiEvent()
    data class OnSelectItem(val filmModel: FilmSelectableModel) : UiEvent()
    object OnDisableSelectedMode : UiEvent()
}

sealed class DataEvent : Event {
    object RequestFilmsFeed : DataEvent()
    object ShowLoadProgress : DataEvent()
    data class SuccessFilmsFeedRequest(val filmList: List<FilmModel>) : DataEvent()
    data class FailFilmsFeedRequest(val throwable: Throwable) : DataEvent()
}

inline fun <reified T> validateState(state: State, action: (T) -> Unit) {
    if (state is T) {
        action(state as T)
    } else {
        throw IllegalStateException("VIEW_STATE != $state")
    }
}

sealed class State {
    data class Load(
        val countOfShimmer: Int = 4
    ) : State()

    data class Content(
        val filmsList: List<FilmModel>
    ) : State()

    data class SelectableContent(
        val selectableFilmsModel: List<FilmSelectableModel>
    ) : State()

    data class Error(
        val throwable: Throwable
    ) : State()
}