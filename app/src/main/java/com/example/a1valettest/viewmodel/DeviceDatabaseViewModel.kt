package com.example.a1valettest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.repository.DeviceDatabaseRepository
import com.example.a1valettest.utils.IODispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDatabaseViewModel @Inject constructor(
    private val deviceDatabaseRepository: DeviceDatabaseRepository,
    @IODispatchers private val ioDispatchers: CoroutineDispatcher
) : ViewModel() {

    fun updateDeviceContent(deviceContent: DeviceContent?) = viewModelScope.launch(ioDispatchers) {
        deviceDatabaseRepository.updateDevice(deviceContent = deviceContent)
    }

    // MyDevices
    fun insertToMyDevice(myDeviceContent: MyDeviceContent?) = viewModelScope.launch {
        deviceDatabaseRepository.insertToMyDevice(myDeviceContent = myDeviceContent)
    }

    val getDevices = deviceDatabaseRepository.getDevices.asLiveData(ioDispatchers)

    fun deleteDevice(myDeviceContent: MyDeviceContent?) = viewModelScope.launch(ioDispatchers) {
        deviceDatabaseRepository.deleteDevice(myDeviceContent = myDeviceContent)
    }
}