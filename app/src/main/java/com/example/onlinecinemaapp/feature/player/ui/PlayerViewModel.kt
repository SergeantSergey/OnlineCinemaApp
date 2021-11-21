package com.example.onlinecinemaapp.feature.player.ui

import com.example.onlinecinemaapp.base.BaseViewModel
import com.example.onlinecinemaapp.base.Event
import com.example.onlinecinemaapp.feature.player.domain.PlayerInteractor

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor
) : BaseViewModel<ViewState>() {
    override fun initialViewState(): ViewState = ViewState

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            is UiEvent.SetPlayerView -> {
                playerInteractor.setView(event.view)
            }

            is UiEvent.SetVideoUrl -> {
                playerInteractor.setUrl(event.url)
            }

            is UiEvent.PlayVideo -> {
                playerInteractor.playerPlay()
            }

            is UiEvent.PauseVide -> {
                playerInteractor.playerPause()
            }

            is UiEvent.StopVideo -> {
                playerInteractor.stopPlayer()
            }
        }

        return null
    }
}