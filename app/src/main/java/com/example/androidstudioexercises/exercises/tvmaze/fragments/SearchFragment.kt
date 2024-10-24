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
import com.example.androidstudioexercises.exercises.tvmaze.model.SearchViewModel
import com.example.androidstudioexercises.exercises.tvmaze.model.SharedUIState
import com.example.androidstudioexercises.exercises.tvmaze.model.SharedViewModel
import com.example.androidstudioexercises.exercises.tvmaze.ui.TvSearch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val uiState by viewModel.searchResults.observeAsState(SharedUIState.Loading)

                TvSearch(
                    uiState = uiState,
                    onShowSelected = { showId ->
                        onNavigateToDetails(showId, uiState)
                    }
                )
            }
        }
    }

    private fun onNavigateToDetails(showId: Int, uiState: SharedUIState) {
        if (uiState is SharedUIState.Success) {
            val selectedShow = uiState.tvShows.find { it.show.id == showId }

            selectedShow?.let {
                sharedViewModel.selectedShow(
                    showDetails = listOf(it),
                    searchQuery = uiState.searchQuery,
                    onSearchQueryChange = uiState.onSearchQueryChange
                )
                findNavController().navigate(R.id.action_to_details)
            }
        }
    }
}