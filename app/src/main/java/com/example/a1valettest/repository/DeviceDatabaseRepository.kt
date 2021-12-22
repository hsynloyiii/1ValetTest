package com.example.a1valettest.repository

import androidx.room.withTransaction
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import javax.inject.Inject

class DeviceDatabaseRepository @Inject constructor(
    private val deviceDataBase: DeviceDataBase,
    private val deviceDao: DeviceDao
) {

    suspend fun insertDevice(deviceContent: DeviceContent?) =
        deviceDataBase.withTransaction {
            deviceDao.insertDevice(deviceContent = deviceContent)
        }

    val getDevices = deviceDao.getAllDevices()

    suspend fun deleteDevice(deviceContent: DeviceContent?) =
        deviceDao.deleteDevice(deviceContent = deviceContent)
}