package com.example.onlinecinemaapp.feature.moviesList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlinecinemaapp.R
import com.example.onlinecinemaapp.databinding.FragmentMoviesListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val viewModel by viewModel<MoviesListViewModel>()
    private val viewBinding by viewBinding<FragmentMoviesListBinding>()

    private val moviesListAdapter by lazy {
        MoviesListAdapter(
            moviesList = mutableListOf(),
            onItemClicked = { movieDomainModel ->
                viewModel.processUiEvent(UiEvent.OnCinemaClicked(movieDomainModel))
            }
        )
    }

    private fun initRecyclerView() {
        viewBinding.moviesRecyclerView.apply {
            adapter = moviesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MoviesListItemDecoration())
        }
    }

    private fun render(viewState: ViewState) {
        if (viewState.errorMessage != null) return
        moviesListAdapter.setMovieList(viewState.moviesList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        viewModel.viewState.observe(viewLifecycleOwner, ::render)
    }
}