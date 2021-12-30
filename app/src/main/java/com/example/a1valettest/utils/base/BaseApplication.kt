package com.example.a1valettest.utils.base

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate.*
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

//    @Inject
//    lateinit var applicationContainer: ApplicationContainer

    override fun onCreate() {
        super.onCreate()
//        applicationContainer.setAppTheme()
//        Log.i("TestingThemeMode", "Application")
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        val nightModeByPosition =
            applicationContext.getSharedPreferences("NightTheme", Context.MODE_PRIVATE)
                .getInt("nightModeByPosition", 2)

        when (nightModeByPosition) {
            0 -> setDefaultNightMode(MODE_NIGHT_NO)
            1 -> setDefaultNightMode(MODE_NIGHT_YES)
            2 -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

}