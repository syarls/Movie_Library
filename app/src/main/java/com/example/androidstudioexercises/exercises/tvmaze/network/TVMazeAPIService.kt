package com.example.androidstudioexercises.exercises.tvmaze.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVMazeAPIService {
    @GET("search/shows")
    suspend fun searchShows(
        @Query("q") query: String
    ): List<TVMaze>

    @GET("shows/{id}/images")
    suspend fun galleryImages(
        @Path("id") id: Int
    ): List<ImageAPI>

}