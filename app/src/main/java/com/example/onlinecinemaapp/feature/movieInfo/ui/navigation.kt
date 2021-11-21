package com.example.onlinecinemaapp.feature.movieInfo.ui

import com.example.onlinecinemaapp.feature.moviesList.domain.module.MovieDomainModel
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun MovieInfoScreen(movie: MovieDomainModel) = FragmentScreen {
    MovieInfoFragment.newInstance(movie)
}