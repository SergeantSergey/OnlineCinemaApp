package com.example.onlinecinemaapp.filmsdescriptionscreen.ui

import androidx.fragment.app.Fragment
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel
import ru.terrakok.cicerone.android.support.SupportAppScreen

class FilmsDescriptionScreen(private val filmsFeedModel: FilmModel) : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return FilmDescriptionFragment.newInstance(filmsFeedModel)
    }
}