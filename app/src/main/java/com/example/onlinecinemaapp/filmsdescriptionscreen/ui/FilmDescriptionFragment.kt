package com.example.onlinecinemaapp.filmsdescriptionscreen.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.distinctUntilChanged
import com.example.onlinecinemaapp.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.example.onlinecinemaapp.base.BaseFragment
import com.example.onlinecinemaapp.extensions.debounce
import com.example.onlinecinemaapp.extensions.dpToPx
import com.example.onlinecinemaapp.extensions.loadImage
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.FilmModel
import com.example.onlinecinemaapp.filmsfeedscreen.ui.model.GenresModel
import com.example.onlinecinemaapp.playerscreen.PlayerActivity
import kotlinx.android.synthetic.main.fragment_film_description.*
import kotlinx.android.synthetic.main.rating_layout.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class FilmDescriptionFragment : BaseFragment(R.layout.fragment_film_description) {

    companion object {
        const val FILM_MODEL = "FILM_MODEL"
        fun newInstance(filmsFeedModel: FilmModel): FilmDescriptionFragment {
            return FilmDescriptionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FILM_MODEL, filmsFeedModel)
                }
            }
        }
    }

    private lateinit var snackBar: Snackbar

    private val filmModel: FilmModel by lazy {
        requireArguments().getParcelable(FILM_MODEL)!!
    }

    private val viewModel: FilmDescriptionViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnBackPressed)
        }

        snackBar = Snackbar.make(
            playBtn,
            getString(R.string.no_internet_connection),
            Snackbar.LENGTH_INDEFINITE
        )

        viewModel.processUiEvent(UiEvent.ShowFilmDescription(filmModel))
        viewModel.viewState.observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.singleLiveEventConnection
            .distinctUntilChanged()
            .debounce()
            .observe(viewLifecycleOwner) { model ->
                with(snackBar) {
                    if (model.isAvailable) dismiss() else show()
                }
            }

        filmAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val percent =
                (abs(appBarLayout.totalScrollRange + verticalOffset).toFloat() / appBarLayout.totalScrollRange)
            playBtn.alpha = percent
            cardViewDescription.radius = percent * resources.dpToPx(16f)
        })

        playBtn.setOnClickListener {
            if (it.alpha == 1.0f) {
                if (snackBar.isShown) {
                    snackBar.dismiss()
                }
                val intent = Intent(requireContext(), PlayerActivity::class.java).apply {
                    putExtra(PlayerActivity.VIDEO_ID, filmModel.id)
                    putExtra(PlayerActivity.VIDEO_TITLE, filmModel.title)
                    putExtra(PlayerActivity.VIDEO_PATH, filmModel.video)
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                }
                startActivity(intent)
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

    private fun render(viewState: ViewState) {
        viewState.filmsFeedModel?.let {
            collapsingToolbar.title = it.title
            tvOverview.text = it.overview
            filmPreviewImageView.loadImage(it.posterPath)

            createGenres(it.genres)
            createStarsRating(it.voteAverage, it.voteCount)
        }
    }

    private fun createGenres(genres: List<GenresModel>) {
        for (genre in genres) {
            genresChipGroup.addView(
                Chip(requireContext()).apply {
                    setChipBackgroundColorResource(R.color.colorPrimary)
                    text = genre.name
                    setTextColor(resources.getColor(R.color.white_100, null))
                }
            )
        }
    }

    private fun createStarsRating(voteAverage: Double, voteCount: Int) {
        for (i in 0..10) {
            containerRatingLayout.starsContainer.addView(
                ImageView(requireContext()).apply {
                    setImageDrawable(
                        ResourcesCompat.getDrawable(
                            resources,
                            if (i < voteAverage)
                                R.drawable.ic_baseline_star_gold_24
                            else
                                R.drawable.ic_baseline_star_grey_24,
                            null
                        )
                    )
                }
            )
        }

        containerRatingLayout.tvVoteAverage.text = voteAverage.toString()
        containerRatingLayout.tvVoteCount.text = voteCount.toString()
    }
}