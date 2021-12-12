package com.example.onlinecinemaapp.filmsfeedscreen.ui.model

import android.os.Parcelable
import com.example.onlinecinemaapp.Item
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmModel(
    val id: Int,
    val title: String,
    val genres: List<GenresModel>,
    val voteAverage: Double,
    val voteCount: Int,
    val posterPath: String,
    val video: String,
    val adult: Boolean,
    val overview: String
) : Item, Parcelable

@Parcelize
data class FilmSelectableModel(
    val filmModel: FilmModel,
    val isSelected: Boolean
) : Item, Parcelable

object Shimmer : Item