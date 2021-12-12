package com.example.onlinecinemaapp.filmsfeedscreen.ui

import com.example.onlinecinemaapp.Item
import com.example.onlinecinemaapp.R
import com.example.onlinecinemaapp.extensions.loadImage
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmSelectableModel
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.Shimmer
import kotlinx.android.synthetic.main.item_film.*
import kotlinx.android.synthetic.main.item_film.view.*
import kotlinx.android.synthetic.main.item_film_selectable.*
import kotlinx.android.synthetic.main.item_shimmer.*

fun filmsAdapterDelegate(
    onClick: (FilmModel) -> Unit,
    onLongClick: (FilmModel) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<FilmModel, Item>(
        R.layout.item_film
    ) {

        filmContainer.setOnClickListener { onClick(item) }
        filmContainer.setOnLongClickListener {
            onLongClick(item)
            true
        }

        bind {
            itemFilmTitle.text = item.title
            tvLabelPopularity.text = item.voteAverage.toString()

            itemFilmPoster.loadImage(item.posterPath)
        }
    }

fun shimmerAdapterDelegate(): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<Shimmer, Item>(
        R.layout.item_shimmer
    ) {
        bind {
            shimmerContainer.showShimmer(true)
        }
    }

fun selectableFilmAdapterDelegate(
    onClick: (FilmSelectableModel) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<FilmSelectableModel, Item>(
        R.layout.item_film_selectable
    ) {

        layoutItemFilm.setOnClickListener {
            onClick(item)
        }
        checkboxSelectFilm.setOnClickListener {
            onClick(item)
        }

        bind {
            checkboxSelectFilm.isChecked = item.isSelected

            layoutItemFilm.itemFilmTitle.text = item.filmModel.title
            layoutItemFilm.tvLabelPopularity.text = item.filmModel.voteAverage.toString()
            layoutItemFilm.itemFilmPoster.loadImage(item.filmModel.posterPath)
        }
    }