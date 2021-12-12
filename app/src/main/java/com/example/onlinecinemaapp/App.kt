package com.example.onlinecinemaapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.onlinecinemaapp.filmsdescriptionscreen.di.filmDescriptionModule
import com.example.onlinecinemaapp.filmsfeedscreen.di.filmsFeedScreenModule
import com.example.onlinecinemaapp.playerscreen.di.videoPlayerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                navModule,
                filmsFeedScreenModule,
                filmDescriptionModule,
                videoPlayerModule
            )
        }

        // чтобы приложение использовало темную тему в зависимости от системы
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}