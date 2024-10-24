package com.example.androidstudioexercises.exercises.tvmaze.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstudioexercises.exercises.tvmaze.data.TVShowRepository
import com.example.androidstudioexercises.exercises.tvmaze.network.TVMaze
import kotlinx.coroutines.launch

sealed class SharedUIState {

    data class Success(
        val tvShows: List<TVMaze>,
        val searchQuery: String,
        val onSearchQueryChange: (String) -> Unit
    ) : SharedUIState()

    data class Empty(
        val searchQuery: String,
        val onSearchQueryChange: (String) -> Unit
    ) : SharedUIState()

    object Loading : SharedUIState()

    object Error : SharedUIState()

}

class SearchViewModel(private val repository: TVShowRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<SharedUIState>(
        SharedUIState.Empty(
            searchQuery = "",
            onSearchQueryChange = { newQuery -> searchTvShows(newQuery) })
    )
    val searchResults: LiveData<SharedUIState> = _searchResults

    init {
        searchTvShows("")
    }

    fun searchTvShows(query: String) {
        viewModelScope.launch {
            try {
                val results = if (query.isEmpty()) {
                    repository.searchTvShows("Gambit")
                } else {
                    repository.searchTvShows(query)
                }

                if (results.isNotEmpty()) {
                    _searchResults.value = SharedUIState.Success(
                        tvShows = results,
                        searchQuery = query,
                        onSearchQueryChange = { newQuery ->
                            searchTvShows(newQuery)
                        }
                    )
                } else {
                    _searchResults.value = SharedUIState.Empty(
                        searchQuery = query,
                        onSearchQueryChange = { newQuery ->
                            searchTvShows(newQuery)
                        }
                    )
                }
            } catch (e: Exception) {
                _searchResults.value = SharedUIState.Error
                Log.e("SearchViewModel", "Error fetching TV shows: ${e.message}", e)
            }
        }
    }
}