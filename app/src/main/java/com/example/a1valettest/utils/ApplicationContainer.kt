package com.example.a1valettest.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApplicationContainer @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun setAppTheme() {
        val nightModeByPosition =
            context.getSharedPreferences("NightTheme", Context.MODE_PRIVATE)
                .getInt("nightModeByPosition", 2)
        when (nightModeByPosition) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

}