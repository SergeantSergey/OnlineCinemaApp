package com.example.onlinecinemaapp.feature.player.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.onlinecinemaapp.R
import com.example.onlinecinemaapp.databinding.FragmentPlayerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PlayerFragment private constructor() : Fragment(R.layout.fragment_player) {

    companion object {

        private const val URL = "url"

        fun newInstance(url: String): PlayerFragment {
            return PlayerFragment().apply {
                arguments = bundleOf(URL to url)
            }
        }
    }

    private val viewBinding by viewBinding<FragmentPlayerBinding>()
    private val viewModel by viewModel<PlayerViewModel> {
        parametersOf(url)
    }

    private val url: String by lazy {
        requireArguments().getString(URL)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.processUiEvent(UiEvent.SetPlayerView(viewBinding.styledPlayer))
        viewModel.processUiEvent(UiEvent.PlayVideo)
    }

    override fun onDestroyView() {
        viewModel.processUiEvent(UiEvent.StopVideo)
        super.onDestroyView()
    }
}