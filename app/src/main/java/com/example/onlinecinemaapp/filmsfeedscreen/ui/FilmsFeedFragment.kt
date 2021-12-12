package com.example.onlinecinemaapp.filmsfeedscreen.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.distinctUntilChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlinecinemaapp.R
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.example.onlinecinemaapp.base.BaseFragment
import com.example.onlinecinemaapp.extensions.*
import com.example.onlinecinemaapp.filmsfeedscreen.ui.*
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.Shimmer
import com.example.onlinecinemaapp.playerscreen.PlayerActivity
import com.example.onlinecinemaapp.playerscreen.PlayerActivity.Companion.VIDEO_LIST
import kotlinx.android.synthetic.main.error_layout.view.*
import kotlinx.android.synthetic.main.fragment_films_feed.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.UnknownHostException

class FilmsFeedFragment : BaseFragment(R.layout.fragment_films_feed) {

    private val viewModel: FilmsFeedScreenViewModel by viewModel()

    private lateinit var snackBar: Snackbar

    private val adapter = ListDelegationAdapter(
        filmsAdapterDelegate(
            onClick = {
                viewModel.processUiEvent(UiEvent.OnItemClick(it))
            },
            onLongClick = { model ->
                viewModel.processUiEvent(UiEvent.OnEnableSelectedMode(model))
            }
        ),
        shimmerAdapterDelegate(),
        selectableFilmAdapterDelegate { model ->
            viewModel.processUiEvent(UiEvent.OnSelectItem(model))
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snackBar = Snackbar.make(
            containerFilmsFeed,
            getString(R.string.no_internet_connection),
            Snackbar.LENGTH_INDEFINITE
        )

        viewModel.viewState.observe(viewLifecycleOwner) { processState(it) }

        viewModel.singleLiveEventConnection
            .distinctUntilChanged()
            .debounce()
            .observe(viewLifecycleOwner) {
                with(snackBar) {
                    if (it.isAvailable) dismiss() else show()
                }
            }

        with(rvFilmsFeed) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setAdapterAndCleanupOnDetachFromWindow(this@FilmsFeedFragment.adapter)
        }

        errorLayout.btnError.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnClickRepeat)
        }

        closeSelectedMode.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnDisableSelectedMode)
        }

        playPlaylist.setOnClickListener {
            viewModel.viewState.value?.let { viewState ->
                validateState<State.SelectableContent>(viewState.state) { state ->
                    openPlayerActivity(
                        state.selectableFilmsModel
                            .filter { it.isSelected }
                            .map { it.filmModel }
                    )
                }
            }
        }
    }

    override fun onAvailable() {
        super.onAvailable()
        viewModel.processUiEvent(UiEvent.OnConnectionChanged(true))
    }

    override fun onLost() {
        super.onLost()
        viewModel.processUiEvent(UiEvent.OnConnectionChanged(false))
    }

    private fun processState(viewState: ViewState) {
        when (viewState.state) {
            is State.Load -> {
                errorLayout.gone()
                rvFilmsFeed.visible()
                adapter.setData(List(viewState.state.countOfShimmer) { Shimmer })
            }
            is State.Content -> {
                titleToolbar.text = getString(R.string.app_name)
                closeSelectedMode.gone()
                playPlaylist.gone()
                errorLayout.gone()
                rvFilmsFeed.visible()
                adapter.setData(viewState.state.filmsList)
            }
            is State.SelectableContent -> {
                titleToolbar.text = getString(R.string.add_to_playlist)
                closeSelectedMode.visible()
                playPlaylist.visible()
                errorLayout.gone()
                rvFilmsFeed.visible()
                adapter.setData(viewState.state.selectableFilmsModel)
            }
            is State.Error -> {
                rvFilmsFeed.gone()
                errorLayout.visible()
                when (viewState.state.throwable) {
                    is UnknownHostException -> {
                        errorLayout.tvError.text =
                            getString(R.string.cant_get_films_feed_error_text)
                    }
                }
            }
        }
    }

    private fun openPlayerActivity(selectedFilms: List<FilmModel>) {
        val intent = Intent(requireContext(), PlayerActivity::class.java).apply {
            putParcelableArrayListExtra(VIDEO_LIST, ArrayList(selectedFilms))
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }
        startActivity(intent)
    }
}