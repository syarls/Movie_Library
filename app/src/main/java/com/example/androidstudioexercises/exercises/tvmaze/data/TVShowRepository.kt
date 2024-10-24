package com.example.androidstudioexercises.exercises.tvmaze.data

import com.example.androidstudioexercises.exercises.tvmaze.network.ImageAPI
import com.example.androidstudioexercises.exercises.tvmaze.network.TVMaze
import com.example.androidstudioexercises.exercises.tvmaze.network.TVMazeAPIService

interface TVShowRepository {
    suspend fun searchTvShows(query: String): List<TVMaze>
    suspend fun galleryImages(id: Int): List<ImageAPI>
}

class NetworkTVShowRepository(
    private val apiService: TVMazeAPIService
) : TVShowRepository {
    override suspend fun searchTvShows(query: String): List<TVMaze> {
        return apiService.searchShows(query)
    }

    override suspend fun galleryImages(id: Int): List<ImageAPI> {
        return apiService.galleryImages(id)
    }
}