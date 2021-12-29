package com.example.a1valettest.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ValetApplication: Application() {

    @Inject
    lateinit var applicationContainer: ApplicationContainer

    override fun onCreate() {
        super.onCreate()
        applicationContainer.setAppTheme()
    }

}