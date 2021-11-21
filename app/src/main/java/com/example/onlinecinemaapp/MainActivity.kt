package com.example.onlinecinemaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.onlinecinemaapp.feature.moviesList.ui.MoviesListScreen
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val router by inject<Router>()
    private val navigatorHolder by inject<NavigatorHolder>()
    private val navigator = AppNavigator(this, R.id.fragmentContainerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.newRootScreen(MoviesListScreen())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}