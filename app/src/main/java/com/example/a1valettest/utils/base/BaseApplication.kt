package com.example.a1valettest.utils.base

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate.*
import com.example.a1valettest.utils.ApplicationContainer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    lateinit var applicationContainer: ApplicationContainer

    override fun onCreate() {
        super.onCreate()
        // change the theme mode as soon as app launches
        applicationContainer.setAppTheme()
    }

}