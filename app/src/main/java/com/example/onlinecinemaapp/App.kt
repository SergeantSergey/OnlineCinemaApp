package com.example.onlinecinemaapp

import android.app.Application
import com.example.onlinecinemaapp.di.appModule
import com.example.onlinecinemaapp.feature.moviesList.di.moviesListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            androidLogger()
            // declare used Android context
            androidContext(this@App)
            // declare modules
            modules(appModule, moviesListModule)
        }

        // log
        Timber.plant(Timber.DebugTree())
    }
}