package com.example.a1valettest.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate.*
import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.utils.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ApplicationContainer @Inject constructor(
    private val context: Context,
    @ApplicationScope private val applicationScope: CoroutineScope,
    private val dataStoreManager: DataStoreManager
) {

    fun setAppTheme() {
        applicationScope.launch {
            dataStoreManager.readFromDataStore().collect {
                when (it.isNight) {
                    true -> setDefaultNightMode(MODE_NIGHT_YES)
                    false -> setDefaultNightMode(MODE_NIGHT_NO)
                    null -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

}