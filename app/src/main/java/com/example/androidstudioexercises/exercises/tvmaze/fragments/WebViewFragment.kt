package com.example.androidstudioexercises.exercises.tvmaze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidstudioexercises.exercises.tvmaze.ui.WebViewScreen


class WebViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val url = arguments?.getString("url") ?: "https://www.tvmaze.com"
                WebViewScreen(url = url) {
                    findNavController().popBackStack()
                }
            }
        }
    }
}