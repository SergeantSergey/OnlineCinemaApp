package com.example.onlinecinemaapp.feature.movieInfo.ui

import com.example.onlinecinemaapp.base.BaseViewModel
import com.example.onlinecinemaapp.base.Event
import com.github.terrakok.cicerone.Router

class MovieInfoViewModel(
    private val router: Router
) : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.OnBackPressed -> {
                router.exit()
            }
        }
        return null
    }
}