package com.example.onlinecinemaapp.feature.moviesList.ui

import com.example.onlinecinemaapp.base.BaseViewModel
import com.example.onlinecinemaapp.base.Event
import com.example.onlinecinemaapp.feature.moviesList.domain.MoviesListInteractor

class MoviesListViewModel(
    private val moviesListInteractor: MoviesListInteractor
) : BaseViewModel<ViewState>() {

    init {
        processUiEvent(UiEvent.RequestCinema)
    }

    override fun initialViewState() =
        ViewState(
            moviesList = emptyList(),
            errorMessage = null
        )

    override suspend fun reduce(event: Event, previousState: ViewState): ViewState? {

        when (event) {

            is UiEvent.RequestCinema -> {
                moviesListInteractor.getCinema().fold(
                    onSuccess = { cinemaDomainModel ->
                        processDataEvent(
                            DataEvent.SuccessCinemaRequest(
                                cinemaDomainModel = cinemaDomainModel
                            )
                        )
                    },
                    onError = { throwable ->
                        processDataEvent(
                            DataEvent.ErrorCinemaRequest(
                                throwable = throwable
                            )
                        )
                    }
                )
            }

            is DataEvent.SuccessCinemaRequest -> {
                return previousState.copy(
                    moviesList = event.cinemaDomainModel.results,
                    errorMessage = null
                )
            }

            is DataEvent.ErrorCinemaRequest -> {
                return previousState.copy(
                    errorMessage = moviesListInteractor.getErrorMessage(event.throwable)
                )
            }
        }

        return null
    }
}