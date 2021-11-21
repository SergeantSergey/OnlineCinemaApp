package com.example.onlinecinemaapp.feature.player.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen

fun PlayerFragment(url: String) = FragmentScreen {
    PlayerFragment.newInstance(url)
}