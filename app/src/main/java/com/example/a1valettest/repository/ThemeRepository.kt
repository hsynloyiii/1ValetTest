package com.example.a1valettest.repository

import com.example.a1valettest.model.ThemeUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThemeRepository @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Repository.ThemeRepository {

    override suspend fun writeToThemeDataStore(themeUIState: ThemeUIState) {
        dataStoreManager.writeToDataStore(themeUIState = themeUIState)
    }

    override fun readItemSelectedPositionFromThemeDataStore(externalScope: CoroutineScope): Int {
        var position: Int? = null
        externalScope.launch {
            dataStoreManager.readFromDataStore().collectLatest {
                position = it.selectedThemeItemPosition
            }
        }
        return position ?: 2
    }


}