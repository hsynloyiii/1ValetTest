package com.example.a1valettest.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1valettest.model.ThemeUIState
import com.example.a1valettest.repository.Repository
import com.example.a1valettest.utils.di.IODispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeRepository: Repository.ThemeRepository,
    @IODispatchers private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun writeToThemeDataStore(nightModeByPosition: Int, selectedThemeItemPosition: Int) =
        viewModelScope.launch(ioDispatcher) {
            themeRepository.writeToThemeDataStore(
                ThemeUIState(
                    nightModeByPosition, selectedThemeItemPosition
                )
            )
        }

    fun readItemPosition(): Int =
        themeRepository.readItemSelectedPositionFromThemeDataStore(externalScope = viewModelScope)
}