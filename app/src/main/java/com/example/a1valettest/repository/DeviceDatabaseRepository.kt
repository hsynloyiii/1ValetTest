package com.example.a1valettest.repository

import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.database.DeviceDao
import javax.inject.Inject

class DeviceDatabaseRepository @Inject constructor(
    private val deviceDao: DeviceDao
) {

    suspend fun insertDevice(deviceContent: DeviceContent?) =
        deviceDao.insertDevice(deviceContent = deviceContent)

    val getDevices = deviceDao.getAllDevices()
}