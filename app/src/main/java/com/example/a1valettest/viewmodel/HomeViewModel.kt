package com.example.a1valettest.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation.findNavController
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.DeviceResponse
import com.example.a1valettest.repository.HomeRepository
import com.example.a1valettest.view.fragment.HomeFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    // We guess that we are fetching from internet so we follow the rest with coroutine and flow

    init {
        viewModelScope.launch {
            homeRepository.deviceContentDataSource()
        }
    }

    val getAllDevices = homeRepository.getAllDevices
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1
        )

}