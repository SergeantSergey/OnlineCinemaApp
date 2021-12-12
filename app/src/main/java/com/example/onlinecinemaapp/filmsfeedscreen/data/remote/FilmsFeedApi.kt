package com.example.onlinecinemaapp.filmsfeedscreen.data.remote

import com.example.onlinecinemaapp.filmsfeedscreen.data.remote.model.FilmsFeedRemoteModel
import retrofit2.http.GET

interface FilmsFeedApi {
    @GET("/LukyanovAnatoliy/eca5141dedc79751b3d0b339649e06a3/raw/38f9419762adf27c34a3f052733b296385b6aa8d/movies.json")
    suspend fun getFilmsFeed(): FilmsFeedRemoteModel
}