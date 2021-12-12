package com.example.onlinecinemaapp.filmsfeedscreen.ui

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class FilmsFeedScreen : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return FilmsFeedFragment()
    }
}