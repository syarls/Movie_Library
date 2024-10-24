package com.example.androidstudioexercises.exercises.tvmaze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidstudioexercises.R
import com.example.androidstudioexercises.exercises.tvmaze.ui.PosterView

class PosterViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val imageUrl = arguments?.getString("url") ?: getString(R.string.tvmaze)

        return ComposeView(requireContext()).apply {
            setContent {
                PosterView(imageUrl) {
                    findNavController().popBackStack()
                }
            }
        }
    }
}