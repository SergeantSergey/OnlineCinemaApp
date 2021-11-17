package com.example.onlinecinemaapp.feature.moviesList.ui

import androidx.annotation.StringRes
import com.example.onlinecinemaapp.base.Event
import com.example.onlinecinemaapp.feature.moviesList.domain.module.CinemaDomainModule
import com.example.onlinecinemaapp.feature.moviesList.domain.module.MovieDomainModel

data class ViewState(
    val moviesList: List<MovieDomainModel>,
    @StringRes val errorMessage: Int?
)

sealed class UiEvent : Event {
    object RequestCinema : UiEvent()
}

sealed class DataEvent : Event {

    data class SuccessCinemaRequest(val cinemaDomainModel: CinemaDomainModule) : DataEvent()

    data class ErrorCinemaRequest(val throwable: Throwable) : DataEvent()
}