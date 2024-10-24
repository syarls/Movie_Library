package com.example.androidstudioexercises.exercises.myApp

import android.app.Application
import com.example.androidstudioexercises.exercises.tvmaze.data.tvModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(tvModule)
        }
    }
}