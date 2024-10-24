package com.example.androidstudioexercises.exercises.tvmaze.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.androidstudioexercises.R
import com.example.androidstudioexercises.exercises.tvmaze.model.SharedUIState
import com.example.androidstudioexercises.exercises.tvmaze.network.Show
import com.example.androidstudioexercises.exercises.tvmaze.network.TVMaze
import kotlinx.coroutines.delay


@Composable
fun TvSearch(
    uiState: SharedUIState, onShowSelected: (Int) -> Unit
) {
    MaterialTheme {
        val isPreview = LocalInspectionMode.current
        var queryText by remember { mutableStateOf("") }
        var userIsTyping by remember { mutableStateOf(false) }

        LaunchedEffect(queryText) {
            delay(300)
            if (userIsTyping) {
                when (uiState) {
                    is SharedUIState.Success -> {
                        uiState.onSearchQueryChange(queryText)
                    }

                    is SharedUIState.Empty -> {
                        uiState.onSearchQueryChange(queryText)
                    }

                    is SharedUIState.Loading -> {}
                    is SharedUIState.Error -> {}
                }
                userIsTyping = false
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 58.dp)
            ) {
                TextField(
                    value = queryText,
                    onValueChange = { newValue ->
                        queryText = newValue
                        userIsTyping = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .height(56.dp)
                        .clip(CircleShape),
                    shape = CircleShape,
                    placeholder = {
                        if (queryText.isEmpty()) {
                            Text(text = stringResource(R.string.search_for_tv_shows))
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black,
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray
                    )
                )

                when (uiState) {
                    is SharedUIState.Success -> {
                        val displayResults = uiState.tvShows
                        if (displayResults.isNotEmpty()) {
                            LazyColumn {
                                items(displayResults) { tvShow ->
                                    TvShowItem(show = tvShow, isPreview = false) { showId ->
                                        onShowSelected(showId)
                                    }
                                }
                            }
                        } else {
                            Text(
                                text = stringResource(R.string.no_results_found),
                                modifier = Modifier.padding(16.dp),
                                color = Color.Gray
                            )
                        }
                    }

                    is SharedUIState.Empty -> {
                        Text(
                            text = stringResource(R.string.no_results_found),
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    }

                    is SharedUIState.Loading -> {
                    }

                    is SharedUIState.Error -> {
                    }
                }
            }

            val painter = if (isPreview) {
                painterResource(id = R.drawable.amogus)
            } else {
                rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.amogus).apply { error(R.drawable.error) }.build()
                )
            }

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(58.dp)
                    .align(Alignment.TopEnd)
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun TvShowItem(show: TVMaze, isPreview: Boolean, onClick: (Int) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick(show.show.id) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = if (isPreview) {
                painterResource(id = R.drawable.imagenull)
            } else {
                rememberAsyncImagePainter(
                    model = show.show.image?.mediumImageUrl ?: R.drawable.imagenull,
                    placeholder = painterResource(id = R.drawable.imagenull),
                )
            }

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(125.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 28.dp, top = 36.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = show.show.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontSize = 26.sp,
                )

                if (show.show.genres.isNotEmpty()) {
                    Text(
                        text = show.show.genres.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTvSearch() {
    val sampleShows = listOf(
        TVMaze(
            Show(
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
        ), TVMaze(
            Show(
                id = 2,
                name = "Game of Thrones",
                genres = listOf("Drama", "Fantasy", "Adventure"),
                image = Show.Image(
                    mediumImageUrl = null, originalImageUrl = null
                ),
                averageRuntime = 55,
                language = "English",
                officialSite = "https://www.hbo.com/game-of-thrones",
                premiered = "2011-04-17",
                rating = Show.Rating(average = 9.3),
                status = "Ended",
                summary = "Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia.",
                url = "https://www.tvmaze.com/shows/82/game-of-thrones"
            )
        )
    )


    val uiState = SharedUIState.Success(sampleShows, searchQuery = "", onSearchQueryChange = {})

    Surface(color = Color.Black) {
        TvSearch(uiState = uiState, onShowSelected = {})
    }
}