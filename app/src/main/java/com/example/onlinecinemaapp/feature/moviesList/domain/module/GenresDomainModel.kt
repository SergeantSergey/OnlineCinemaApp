package com.example.onlinecinemaapp.feature.moviesList.domain.module

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenresDomainModel(
    val name: String,
) : Parcelable