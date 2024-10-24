package com.example.androidstudioexercises.exercises.tvmaze.ui

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.androidstudioexercises.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(url: String, onBack: () -> Unit) {
    Box {
        AndroidView(factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl(url)


                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?, request: WebResourceRequest?
                    ): Boolean {
                        val urlToLoad = request?.url.toString()
                        val finalUrl = if (urlToLoad.startsWith("http://")) {
                            urlToLoad.replace("http://", "https://")
                        } else {
                            urlToLoad
                        }
                        view?.loadUrl(finalUrl)
                        return true
                    }
                }
            }
        }, modifier = Modifier.fillMaxSize())

        TopAppBar(title = { Text(text = stringResource(R.string.website_view)) }, navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWebViewScreen() {
    val sampleUrl = "null"
    val dummyOnBack = { }

    WebViewScreen(url = sampleUrl, onBack = dummyOnBack)
}