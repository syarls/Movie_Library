package com.example.androidstudioexercises.exercises.tvmaze.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidstudioexercises.exercises.tvmaze.network.TVMaze


class SharedViewModel : ViewModel() {
    private val _sharedUiState = MutableLiveData<SharedUIState>(SharedUIState.Loading)
    val sharedUiState: LiveData<SharedUIState> get() = _sharedUiState

    fun selectedShow(
        showDetails: List<TVMaze>,
        searchQuery: String,
        onSearchQueryChange: (String) -> Unit
    ) {
        _sharedUiState.value = SharedUIState.Success(showDetails, searchQuery, onSearchQueryChange)
    }
}