package com.example.onlinecinemaapp

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

private const val BASE_URL = "https://gist.githubusercontent.com/"
const val FILMS_FEED_QUALIFIER = "FILMS_FEED_QUALIFIER"

val appModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor(
                object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.d("OkHttp", message)
                    }
                }
            ).apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            })
            .build()
    }
}

val navModule = module {

    single<Cicerone<Router>>(named(FILMS_FEED_QUALIFIER)) {
        Cicerone.create()
    }

    single<NavigatorHolder>(named(FILMS_FEED_QUALIFIER)) {
        get<Cicerone<Router>>(named(FILMS_FEED_QUALIFIER)).navigatorHolder
    }

    single<Router>(named(FILMS_FEED_QUALIFIER)) {
        get<Cicerone<Router>>(named(FILMS_FEED_QUALIFIER)).router
    }
}
