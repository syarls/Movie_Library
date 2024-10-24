package com.example.androidstudioexercises.exercises.tvmaze.data

import android.util.Log
import com.example.androidstudioexercises.exercises.tvmaze.model.DetailsViewModel
import com.example.androidstudioexercises.exercises.tvmaze.model.SearchViewModel
import com.example.androidstudioexercises.exercises.tvmaze.model.SharedViewModel
import com.example.androidstudioexercises.exercises.tvmaze.network.TVMazeAPIService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val tvModule = module {

    single(named("baseUrl")) { "https://api.tvmaze.com/" }

    single {
        Moshi.Builder()
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        HttpLoggingInterceptor { message ->
            Log.d("OkHttp", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named("baseUrl")))
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single<TVMazeAPIService> {
        get<Retrofit>().create(TVMazeAPIService::class.java)
    }

    single<TVShowRepository> {
        NetworkTVShowRepository(get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        SharedViewModel()
    }
    viewModel {
        DetailsViewModel(get())
    }

}
