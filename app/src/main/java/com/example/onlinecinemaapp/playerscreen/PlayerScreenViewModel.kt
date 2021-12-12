package com.example.onlinecinemaapp.playerscreen

import com.example.onlinecinemaapp.base.BaseViewModel
import com.example.onlinecinemaapp.base.ConnectionNotification
import com.example.onlinecinemaapp.base.Event
import com.example.onlinecinemaapp.player.VideoPlayer

class PlayerScreenViewModel(private val videoPlayer: VideoPlayer) : BaseViewModel<ViewState>() {
    override fun initialViewState() =
        ViewState(status = Status.PLAYER_BUFFERED, currentTitle = "", errorType = 0)

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            // вызывает изменение singleLiveEvent когда меняется состояние подключение к интернету
            is UiEvent.OnConnectionChanged -> singleLiveEventConnection.postValue(
                ConnectionNotification(event.isConnectionAvailable)
            )

            // инициализация плеера
            is UiEvent.OnPlayerInit -> {
                with(videoPlayer) {
                    setView(event.view)
                    setPlayerListener(
                        onLoad = {
                            processUiEvent(UiEvent.OnPlayerBuffered)
                        },
                        onLoadEnded = {
                            processUiEvent(UiEvent.OnPlayerBufferedEnded)
                        },
                        onTitleChanged = {
                            processUiEvent(UiEvent.PlayerTitleChanged(it))
                        },
                        onError = { type ->
                            processDataEvent(ErrorEvent.OnPlayerError(type))
                        }
                    )
                }

                return previousState.copy(status = Status.PLAYER_INIT)
            }

            // проигрывание плеера
            is UiEvent.OnPlayerPlay -> videoPlayer.play(event.videoId, event.title, event.url)

            // смена заголовка плеера
            is UiEvent.PlayerTitleChanged -> return previousState.copy(currentTitle = event.title)

            // загрузка плеера
            is UiEvent.OnPlayerBuffered -> return previousState.copy(status = Status.PLAYER_BUFFERED)

            // окончание загрузки плеера
            is UiEvent.OnPlayerBufferedEnded -> return previousState.copy(status = Status.PLAYER_BUFFERED_ENDED)

            // ошибка плеера
            is ErrorEvent.OnPlayerError -> return previousState.copy(
                status = Status.PLAYER_ERROR,
                errorType = event.error
            )

            // пауза плеера
            is UiEvent.OnVideoPlayerPause -> videoPlayer.pause()

            // проигрывание плейлиста
            is UiEvent.OnPlayerPlaylist -> videoPlayer.playPlaylist(event.urlList)
        }
        return null
    }

    override fun onCleared() {
        super.onCleared()
        videoPlayer.release()
    }
}