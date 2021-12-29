package com.example.a1valettest.utils

import androidx.appcompat.app.AppCompatDelegate.*
import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.utils.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ApplicationContainer @Inject constructor(
    @ApplicationScope private val applicationScope: CoroutineScope,
    private val dataStoreManager: DataStoreManager
) {

    fun setAppTheme() {
        applicationScope.launch {
            dataStoreManager.readFromDataStore().collect {
                when (it.nightModeByPosition) {
                    0 -> setDefaultNightMode(MODE_NIGHT_NO)
                    1 -> setDefaultNightMode(MODE_NIGHT_YES)
                    2 -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

}