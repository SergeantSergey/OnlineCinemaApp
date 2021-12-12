package com.example.onlinecinemaapp.filmsfeedscreen.ui

import androidx.lifecycle.viewModelScope
import com.example.onlinecinemaapp.base.BaseViewModel
import com.example.onlinecinemaapp.base.ConnectionNotification
import com.example.onlinecinemaapp.base.Event
import com.example.onlinecinemaapp.filmsdescriptionscreen.ui.FilmsDescriptionScreen
import com.example.onlinecinemaapp.filmsfeedscreen.data.FilmsFeedInteractor
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmSelectableModel
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router

class FilmsFeedScreenViewModel(
    private val interactor: FilmsFeedInteractor,
    private val router: Router
) : BaseViewModel<ViewState>() {

    init {
        processDataEvent(DataEvent.RequestFilmsFeed)
    }

    override fun initialViewState() = ViewState(State.Load())

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {

            // запрос списка фильмов
            is DataEvent.RequestFilmsFeed,
            is UiEvent.OnClickRepeat -> {
                processDataEvent(DataEvent.ShowLoadProgress)
                viewModelScope.launch {
                    interactor.getFilmsFeed().fold(
                        {
                            processDataEvent(DataEvent.FailFilmsFeedRequest(it))
                        },
                        {
                            processDataEvent(DataEvent.SuccessFilmsFeedRequest(it))
                        }
                    )
                }
            }

            // показывает прогресс загрузки
            is DataEvent.ShowLoadProgress -> return previousState.copy(state = State.Load())

            // запрос списка фильмов завершился успешно
            is DataEvent.SuccessFilmsFeedRequest -> return previousState.copy(
                state = State.Content(event.filmList)
            )

            // запрос списка фильмов завершился c ошибкой
            is DataEvent.FailFilmsFeedRequest -> return previousState.copy(state = State.Error(event.throwable))

            // нажатие по элементу
            is UiEvent.OnItemClick -> validateState<State.Content>(previousState.state) {
                router.navigateTo(FilmsDescriptionScreen(filmsFeedModel = event.model))
            }

            // изменение состояния подключения к интернету
            is UiEvent.OnConnectionChanged -> singleLiveEventConnection.postValue(
                ConnectionNotification(
                    event.isConnectionAvailable
                )
            )

            // активания режима выбора
            is UiEvent.OnEnableSelectedMode -> validateState<State.Content>(previousState.state) { state ->
                return previousState.copy(
                    state = State.SelectableContent(
                        selectableFilmsModel = state.filmsList.map { model ->
                            FilmSelectableModel(
                                filmModel = model,
                                isSelected = event.model == model
                            )
                        }
                    )
                )
            }

            // выбор фильма
            is UiEvent.OnSelectItem -> validateState<State.SelectableContent>(previousState.state) { state ->
                val list = state.selectableFilmsModel.map {
                    FilmSelectableModel(
                        it.filmModel,
                        if (event.filmModel == it) !it.isSelected else it.isSelected
                    )
                }

                if (list.none { it.isSelected }) {
                    processUiEvent(UiEvent.OnDisableSelectedMode)
                } else {
                    return previousState.copy(
                        state = state.copy(
                            selectableFilmsModel = list
                        )
                    )
                }
            }

            // отключение режима выбора
            is UiEvent.OnDisableSelectedMode -> validateState<State.SelectableContent>(previousState.state) { state ->
                return previousState.copy(
                    state = State.Content(state.selectableFilmsModel.map { it.filmModel })
                )
            }
        }

        return null
    }
}