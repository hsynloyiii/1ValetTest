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

    suspend fun insertToMyDevice(myDeviceContent: MyDeviceContent?) =
        deviceDataBase.withTransaction {
            deviceDao.insertToMyDevice(myDeviceContent = myDeviceContent)
        }

    val getDevices = deviceDao.getAllMyDevices(isFavorite = true)

    suspend fun deleteDevice(myDeviceContent: MyDeviceContent?) =
        deviceDao.deleteDevice(myDeviceContent = myDeviceContent)

    suspend fun updateDevice(deviceContent: DeviceContent?) =
        deviceDao.updateDeviceContent(deviceContent = deviceContent)

}