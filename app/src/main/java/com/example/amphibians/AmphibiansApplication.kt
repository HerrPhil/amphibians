package com.example.amphibians

import android.app.Application
import com.example.amphibians.data.AppContainer
import com.example.amphibians.data.DefaultAppContainer

class AmphibiansApplication : Application() {

    lateinit var container: AppContainer

    // 19th re-factoring: create default app container for whole application; add to manifest
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}