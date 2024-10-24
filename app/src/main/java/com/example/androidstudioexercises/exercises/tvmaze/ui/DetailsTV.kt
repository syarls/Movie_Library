package com.example.androidstudioexercises.exercises.tvmaze.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.androidstudioexercises.R
import com.example.androidstudioexercises.exercises.tvmaze.model.DetailsUiState
import com.example.androidstudioexercises.exercises.tvmaze.network.ImageAPI
import com.example.androidstudioexercises.exercises.tvmaze.network.Resolutions
import com.example.androidstudioexercises.exercises.tvmaze.network.Show
import com.example.androidstudioexercises.exercises.tvmaze.network.TVMaze

@Composable
fun ShowDetails(
    detailsUiState: DetailsUiState?,
    onBackClick: () -> Unit,
    onNavigateToWebView: (String) -> Unit,
    onNavigateToPosterView: (String) -> Unit
) {
    when (detailsUiState) {
        is DetailsUiState.Success -> {
            val selectedShow = detailsUiState.tvShows
            ShowDetailsContent(
                selectedShow = selectedShow,
                imageResults = detailsUiState.imageResults,
                onBackClick = onBackClick,
                onNavigateToWebView = onNavigateToWebView,
                onNavigateToPosterView = onNavigateToPosterView
            )
        }

        else -> {
            Image(
                painter = painterResource(R.drawable.loading),
                contentDescription = "Loading",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Composable
fun ShowDetailsContent(
    selectedShow: TVMaze?,
    imageResults: List<ImageAPI>,
    onBackClick: () -> Unit,
    onNavigateToWebView: (String) -> Unit,
    onNavigateToPosterView: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            val imageUrl = selectedShow?.show?.image?.mediumImageUrl ?: R.drawable.imagenull
            val painter = rememberAsyncImagePainter(
                model = imageUrl,
                placeholder = painterResource(R.drawable.loading)
            )

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = selectedShow?.show?.name ?: stringResource(R.string.unknown_show),
                style = TextStyle(
                    fontSize = 32.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 12.dp)
            )

            selectedShow?.show?.rating?.average?.let { averageRating ->
                RatingStars(rating = averageRating.toInt())
            } ?: Text(
                text = stringResource(R.string.no_rating_available),
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = stringResource(R.string.overview),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Yellow,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = selectedShow?.show?.summary?.replace(Regex("<[^>]*>"), "")
                    ?: stringResource(R.string.not_specified),
                style = TextStyle(fontSize = 20.sp, color = Color.Gray),
                modifier = Modifier.padding(vertical = 12.dp)
            )

            Text(
                text = stringResource(R.string.show_details),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Yellow,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = stringResource(R.string.language),
                style = TextStyle(fontSize = 20.sp, color = Color.Gray),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = selectedShow?.show?.language ?: stringResource(R.string.not_specified),
                style = TextStyle(fontSize = 20.sp, color = Color.Gray)
            )

            Text(
                text = stringResource(R.string.genres),
                style = TextStyle(fontSize = 20.sp, color = Color.Gray),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = selectedShow?.show?.genres?.joinToString(", ") ?: "",
                style = TextStyle(fontSize = 20.sp, color = Color.Gray)
            )
            Text(
                text = if (selectedShow?.show?.averageRuntime != null) {
                    stringResource(
                        R.string.average_runtime_visible,
                        selectedShow.show.averageRuntime
                    )
                } else {
                    stringResource(R.string.average_runtime_not_visible)
                },
                style = TextStyle(fontSize = 20.sp, color = Color.Gray),
                modifier = Modifier.padding(top = 8.dp)
            )


            Text(
                text = stringResource(R.string.website),
                style = TextStyle(fontSize = 20.sp, color = Color.Gray),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = selectedShow?.show?.officialSite ?: stringResource(R.string.not_specified),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = if (selectedShow?.show?.officialSite != null) colorResource(id = R.color.blue_link) else Color.Gray,
                    textDecoration = if (selectedShow?.show?.officialSite != null) TextDecoration.Underline else TextDecoration.None
                ),
                modifier = Modifier.clickable {
                    selectedShow?.show?.officialSite?.let { officialUrl ->
                        onNavigateToWebView(officialUrl)
                    }
                }
            )


            Text(
                text = stringResource(R.string.link),
                style = TextStyle(fontSize = 20.sp, color = Color.Gray),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = selectedShow?.show?.url ?: stringResource(R.string.not_specified),
                style = if (selectedShow?.show?.url != null) {
                    TextStyle(
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.blue_link),
                        textDecoration = TextDecoration.Underline
                    )
                } else {
                    MaterialTheme.typography.bodyMedium
                },
                modifier = Modifier.clickable {
                    selectedShow?.show?.url?.let { url ->
                        onNavigateToWebView(url)
                    }
                }
            )

            Text(
                text = stringResource(R.string.gallery),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Yellow,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        item {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                val filteredImageResults = imageResults.filter { it.resolutions.medium != null }
                if (filteredImageResults.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_images_found),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    filteredImageResults.chunked(3).forEach { rowImages ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowImages.forEach { image ->
                                val originalImageUrl = image.resolutions.original?.url
                                val mediumImageUrl = image.resolutions.medium?.url

                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = mediumImageUrl,
                                        placeholder = painterResource(R.drawable.loading)
                                    ),
                                    contentDescription = stringResource(R.string.gallery_image),
                                    modifier = Modifier
                                        .size(125.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable {
                                            originalImageUrl?.let { url ->
                                                onNavigateToPosterView(url)
                                            }
                                        },
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RatingStars(rating: Int) {
    val starCount = when (rating) {
        in 1..2 -> 1
        in 3..4 -> 2
        in 5..6 -> 3
        in 7..8 -> 4
        in 9..10 -> 5
        else -> 0
    }

    Row(
        modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        repeat(starCount) {
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = "Star",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShowDetails() {
    val sampleShow = TVMaze(
        show = Show(
            id = 1,
            name = "Breaking Bad",
            genres = listOf("Crime", "Thriller"),
            image = Show.Image(
                mediumImageUrl = null, originalImageUrl = null
            ),
            averageRuntime = 47,
            language = "English",
            officialSite = "https://www.amc.com/shows/breaking-bad",
            premiered = "2008-01-20",
            rating = Show.Rating(average = 9.5),
            status = "Ended",
            summary = "A high school chemistry teacher turned methamphetamine producer.",
            url = "https://www.tvmaze.com/shows/169/breaking-bad"
        )
    )

    val dummyImageResults = listOf(
        ImageAPI(
            id = 1,
            resolutions = Resolutions(
                medium = null,
                original = null
            )
        )
    )
    val sharedUIState =
        DetailsUiState.Success(tvShows = sampleShow, imageResults = dummyImageResults)

    Surface(color = Color.Black) {
        ShowDetails(
            detailsUiState = sharedUIState,
            onBackClick = {},
            onNavigateToWebView = {},
            onNavigateToPosterView = {}
        )
    }
}
