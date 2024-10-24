package com.example.androidstudioexercises.exercises.tvmaze.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidstudioexercises.exercises.tvmaze.data.TVShowRepository
import com.example.androidstudioexercises.exercises.tvmaze.network.ImageAPI
import com.example.androidstudioexercises.exercises.tvmaze.network.TVMaze
import kotlinx.coroutines.launch

sealed class DetailsUiState {
    data class Success(val tvShows: TVMaze?, val imageResults: List<ImageAPI>) : DetailsUiState()

    object Error : DetailsUiState()
}

class DetailsViewModel(private val repository: TVShowRepository) : ViewModel() {
    private val _uiState = MutableLiveData<DetailsUiState>()
    val uiState: LiveData<DetailsUiState> = _uiState

    fun getDetails(tvShows: TVMaze?) {
        _uiState.value = DetailsUiState.Success(tvShows, emptyList())

        viewModelScope.launch {
            try {
                val images = getGalleryImages(tvShows?.show?.id)
                _uiState.value = DetailsUiState.Success(tvShows, images)

            } catch (e: Exception) {
                _uiState.value = DetailsUiState.Error
                Log.e("DetailsViewModel", "Error searching TV shows: ${e.message}", e)
            }
        }
    }

    private suspend fun getGalleryImages(id: Int?): List<ImageAPI> {
        return id?.let {
            try {
                repository.galleryImages(it)
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Error fetching images for show ID $it: ${e.message}", e)
                emptyList()
            }
        } ?: run {
            emptyList()
        }
    }
}