package com.example.a1valettest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.DeviceResponse
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.repository.Repository
import com.example.a1valettest.utils.di.IODispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDatabaseViewModel @Inject constructor(
    private val deviceDatabaseRepository: Repository.DeviceDataBaseRepository,
    @IODispatchers private val ioDispatchers: CoroutineDispatcher
) : ViewModel() {

    // All Devices
    fun getDeviceDataSource() = viewModelScope.launch(ioDispatchers) {
        // we need cold flow just for insert deviceContentDataSource to our list ( not sharing or data observing )
        deviceDatabaseRepository.getAllDevices().collect {
            if (it.isNullOrEmpty()) {
                insertToAllDevices(
                    deviceContentList = DeviceResponse(devices = deviceDatabaseRepository.deviceContentDataSource()).devices
                )
            }
        }
    }

    fun insertToAllDevices(deviceContentList: List<DeviceContent>?) =
        viewModelScope.launch(ioDispatchers) {
            deviceDatabaseRepository.insertAllDevices(deviceContentList = deviceContentList)
        }

    val getAllDevices = deviceDatabaseRepository.getAllDevices()
//        .asLiveData(ioDispatchers)
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1
        )

    fun updateDeviceContent(deviceContent: DeviceContent?) = viewModelScope.launch(ioDispatchers) {
        deviceDatabaseRepository.updateDevice(deviceContent = deviceContent)
    }

    // My Devices
    fun insertToMyDevice(myDeviceContent: MyDeviceContent?) = viewModelScope.launch(ioDispatchers) {
        deviceDatabaseRepository.insertToMyDevice(myDeviceContent = myDeviceContent)
    }

    val getMyDevices = deviceDatabaseRepository.getMyDevices(isFavorite = true)
        .shareIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1
        )

    fun deleteDevice(myDeviceContent: MyDeviceContent?) = viewModelScope.launch(ioDispatchers) {
        deviceDatabaseRepository.deleteDevice(myDeviceContent = myDeviceContent)
    }
}