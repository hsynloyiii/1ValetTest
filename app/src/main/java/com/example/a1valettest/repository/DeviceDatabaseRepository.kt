package com.example.a1valettest.repository

import androidx.room.withTransaction
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import javax.inject.Inject

class DeviceDatabaseRepository @Inject constructor(
    private val deviceDataBase: DeviceDataBase,
    private val deviceDao: DeviceDao
) {

    // Update current favorite state of specific item in AllDevices
    suspend fun updateDevice(deviceContent: DeviceContent?) =
        deviceDao.updateDeviceContent(deviceContent = deviceContent)


    // MyDevices
    val getDevices = deviceDao.getAllMyDevices(isFavorite = true)

    suspend fun insertToMyDevice(myDeviceContent: MyDeviceContent?) =
        deviceDataBase.withTransaction {
            deviceDao.insertToMyDevice(myDeviceContent = myDeviceContent)
        }

    suspend fun deleteDevice(myDeviceContent: MyDeviceContent?) =
        deviceDao.deleteDevice(myDeviceContent = myDeviceContent)


}