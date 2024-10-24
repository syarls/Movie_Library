package com.example.androidstudioexercises.exercises.tvmaze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.androidstudioexercises.R
import com.example.androidstudioexercises.exercises.tvmaze.model.DetailsViewModel
import com.example.androidstudioexercises.exercises.tvmaze.model.SharedUIState
import com.example.androidstudioexercises.exercises.tvmaze.model.SharedViewModel
import com.example.androidstudioexercises.exercises.tvmaze.ui.ShowDetails
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {
    private val viewModel by viewModel<DetailsViewModel>()
    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.observeAsState()

                ShowDetails(
                    detailsUiState = uiState,
                    onBackClick = ::onBackClick,
                    onNavigateToPosterView = ::onNavigateToPosterView,
                    onNavigateToWebView = ::onNavigateToWebView
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.sharedUiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is SharedUIState.Success -> {
                    val showDetails = uiState.tvShows.firstOrNull()
                    viewModel.getDetails(showDetails)
                }

                is SharedUIState.Error -> {}
                else -> {}
            }
        }
    }

    private fun onBackClick() {
        findNavController().popBackStack()
    }

    private fun onNavigateToWebView(url: String) {
        findNavController().navigate(R.id.action_to_webViewFragment, Bundle().apply {
            putString("url", url)
        })
    }

    private fun onNavigateToPosterView(url: String) {
        findNavController().navigate(R.id.action_to_PosterViewFragment, Bundle().apply {
            putString("url", url)
        })
    }
}