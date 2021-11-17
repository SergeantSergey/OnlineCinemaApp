package com.example.onlinecinemaapp.feature.moviesList.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.onlinecinemaapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val viewModel by viewModel<MoviesListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }

    private fun render(viewState: ViewState) {
        Timber.e("TIMBER -> $viewState")
    }
}