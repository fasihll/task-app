package com.otakkanan.taskapp.injection.app

import android.app.Application
import com.kizitonwose.calendarview.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var tree: Timber.DebugTree

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(tree)
    }
}