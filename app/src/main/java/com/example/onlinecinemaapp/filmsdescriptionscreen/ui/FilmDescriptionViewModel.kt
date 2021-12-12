package com.example.onlinecinemaapp.filmsdescriptionscreen.ui

import com.example.onlinecinemaapp.base.BaseViewModel
import com.example.onlinecinemaapp.base.ConnectionNotification
import com.example.onlinecinemaapp.base.Event
import ru.terrakok.cicerone.Router

class FilmDescriptionViewModel(private val router: Router) : BaseViewModel<ViewState>() {

    override fun initialViewState() = ViewState()

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            // показ описания фильма
            is UiEvent.ShowFilmDescription -> return previousState.copy(filmsFeedModel = event.filmsFeedModel)

            // изменение состояния подключения к интернету
            is UiEvent.OnConnectionChanged -> singleLiveEventConnection.postValue(
                ConnectionNotification(event.isConnectionAvailable)
            )

            // выход назад
            is UiEvent.OnBackPressed -> router.exit()
        }
        return null
    }
}