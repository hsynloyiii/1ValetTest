package com.example.a1valettest.repository

import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface Repository {

    interface DeviceDataBaseRepository {
        // All Devices
        fun deviceContentDataSource(): List<DeviceContent>

        suspend fun insertAllDevices(deviceContentList: List<DeviceContent>?)

        fun getAllDevices(): Flow<List<DeviceContent>>

        // Update current favorite state of specific item in AllDevices when favorite button trigger
        suspend fun updateDevice(deviceContent: DeviceContent?)


        // My Devices
        fun getMyDevices(isFavorite: Boolean): Flow<List<MyDeviceContent>>

        suspend fun insertToMyDevice(myDeviceContent: MyDeviceContent?)

        suspend fun deleteDevice(myDeviceContent: MyDeviceContent?)
    }

}