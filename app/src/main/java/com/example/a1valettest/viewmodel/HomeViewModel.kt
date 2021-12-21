package com.example.a1valettest.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.a1valettest.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    // We guess that we are fetching from internet so we follow the rest with multi-threading
    val getDeviceContent = flow {
        emit(homeRepository.fetchDeviceContent())
    }
        .catch { e ->
            print(e.message)
        }

}