package com.example.a1valettest.utils.base

import android.app.Application
import com.example.a1valettest.utils.ApplicationContainer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication: Application() {

    @Inject
    lateinit var applicationContainer: ApplicationContainer

    override fun onCreate() {
        super.onCreate()
        applicationContainer.setAppTheme()
    }

}