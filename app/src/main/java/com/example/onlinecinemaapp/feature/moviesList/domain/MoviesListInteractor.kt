package com.example.onlinecinemaapp.feature.moviesList.domain

import com.example.onlinecinemaapp.R
import com.example.onlinecinemaapp.base.attempt
import com.example.onlinecinemaapp.feature.moviesList.data.api.CinemaRepo
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MoviesListInteractor(
    private val cinemaRepo: CinemaRepo
) {

    suspend fun getCinema() = attempt {
        cinemaRepo.getCinema()
    }

    fun getErrorMessage(throwable: Throwable): Int {

        return when (throwable) {

            is SocketTimeoutException,
            is UnknownHostException -> {
                R.string.no_internet_connection
            }

            else -> throw Exception("Не обработанное исключение!")
        }
    }
}