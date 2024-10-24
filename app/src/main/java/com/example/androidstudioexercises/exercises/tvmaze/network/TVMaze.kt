package com.example.androidstudioexercises.exercises.tvmaze.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

// Search
@Parcelize
@JsonClass(generateAdapter = true)
data class TVMaze(
    @Json(name = "show") val show: Show
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Show(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "language") val language: String?,
    @Json(name = "genres") val genres: List<String>,
    @Json(name = "status") val status: String?,
    @Json(name = "premiered") val premiered: String?,
    @Json(name = "rating") val rating: Rating?,
    @Json(name = "image") val image: Image?,
    @Json(name = "url") val url: String?,
    @Json(name = "summary") val summary: String?,
    @Json(name = "averageRuntime") val averageRuntime: Int?,
    @Json(name = "officialSite") val officialSite: String?
) : Parcelable {

    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Rating(
        @Json(name = "average") val average: Double?
    ) : Parcelable

    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Image(
        @Json(name = "medium") val mediumImageUrl: String?,
        @Json(name = "original") val originalImageUrl: String?
    ) : Parcelable
}
