package com.example.cointwo

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Timber.plant(Timber.DebugTree())
        FIRE_INIT = FirebaseApp.initializeApp(this)!!
//        FirebaseApp.initializeApp(this)

    }

    companion object{
         lateinit var INSTANCE : App
         lateinit var FIRE_INIT : FirebaseApp
    }

}