package com.example.androidstudioexercises.exercises.tvmaze.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.androidstudioexercises.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PosterView(imageUrl: String, onBackClick: () -> Unit) {
    val isPreview = LocalInspectionMode.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        val painter = if (isPreview) {
            painterResource(id = R.drawable.imagenull)
        } else {
            rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                    placeholder(R.drawable.loading)
                    error(R.drawable.error)
                }).build()
            )
        }

        Image(
            painter = painter,
            contentDescription = "Image Poster",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent, navigationIconContentColor = Color.White
        ), modifier = Modifier.align(Alignment.TopStart)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPosterView() {
    Surface(color = Color.Black) {
        PosterView(imageUrl = "null", onBackClick = {})
    }
}
