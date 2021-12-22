package com.example.a1valettest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.repository.DeviceDatabaseRepository
import com.example.a1valettest.utils.IODispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDatabaseViewModel @Inject constructor(
    private val deviceDatabaseRepository: DeviceDatabaseRepository,
    @IODispatchers private val ioDispatchers: CoroutineDispatcher
) : ViewModel() {

    fun insertDevice(deviceContent: DeviceContent?) = viewModelScope.launch(ioDispatchers) {
        deviceDatabaseRepository.insertDevice(deviceContent = deviceContent)
    }

    val getDevices = deviceDatabaseRepository.getDevices
}