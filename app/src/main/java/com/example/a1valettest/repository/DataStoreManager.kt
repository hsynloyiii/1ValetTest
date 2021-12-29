package com.example.a1valettest.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.a1valettest.model.ThemeUIState
import kotlinx.coroutines.flow.map

class DataStoreManager(val context: Context) {
    private val Context.dataStoreTheme: DataStore<Preferences> by preferencesDataStore(name = "ThemeUIState")

    private val IS_NIGHT = booleanPreferencesKey(name = "isNight")
    private val SELECTED_ITEM_POSITION = intPreferencesKey(name = "themeItemPosition")

    suspend fun writeToDataStore(themeUIState: ThemeUIState) = context.dataStoreTheme.edit {
        it[IS_NIGHT] = themeUIState.isNight ?: false
        it[SELECTED_ITEM_POSITION] = themeUIState.selectedThemeItemPosition
    }

    fun readFromDataStore() = context.dataStoreTheme.data.map {
        ThemeUIState(
            isNight = it[IS_NIGHT] ?: false,
            selectedThemeItemPosition = it[SELECTED_ITEM_POSITION] ?: 2
        )
    }
}