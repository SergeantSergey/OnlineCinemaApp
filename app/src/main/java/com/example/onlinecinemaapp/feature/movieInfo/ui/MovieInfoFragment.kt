package com.example.onlinecinemaapp.feature.movieInfo.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlinecinemaapp.R
import com.example.onlinecinemaapp.databinding.FragmentMovieInfoBinding
import com.example.onlinecinemaapp.feature.moviesList.domain.module.MovieDomainModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieInfoFragment private constructor() : Fragment(R.layout.fragment_movie_info) {

    companion object {

        private const val AP_KEY = "movieInfo"

        fun newInstance(movie: MovieDomainModel): MovieInfoFragment {
            return MovieInfoFragment().apply {
                arguments = bundleOf(AP_KEY to movie)
            }
        }
    }

    private val viewModel by viewModel<MovieInfoViewModel>()
    private val binding by viewBinding<FragmentMovieInfoBinding>()

    private val movie: MovieDomainModel by lazy {
        requireArguments().getParcelable(AP_KEY)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvTitle.text = movie.title
        }
    }
}