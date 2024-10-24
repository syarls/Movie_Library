package com.example.androidstudioexercises.exercises.tvmaze.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//Images

@JsonClass(generateAdapter = true)
data class ImageAPI(
    @Json(name = "id") val id: Int,
    @Json(name = "resolutions") val resolutions: Resolutions
)

@JsonClass(generateAdapter = true)
data class Resolutions(
    @Json(name = "original") val original: ImageResolution?,
    @Json(name = "medium") val medium: ImageResolution?
)

@JsonClass(generateAdapter = true)
data class ImageResolution(
    @Json(name = "url") val url: String?,
)
