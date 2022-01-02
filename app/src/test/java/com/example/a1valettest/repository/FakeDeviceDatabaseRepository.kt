package com.example.a1valettest.repository

import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// As is not necessary to use Hilt for Unit Tests, We create fake classes that don't need any constructor
class FakeDeviceDatabaseRepository : Repository.DeviceDataBaseRepository {

    private val myDeviceContentsList = mutableListOf<MyDeviceContent>()
    private val allDeviceContentList = mutableListOf<DeviceContent>()



    private var myDeviceContentsFlow = flow {
        emit(myDeviceContentsList)
    }

    private var allDeviceContentsFlow = flow {
        emit(allDeviceContentList)
    }

    private fun refreshAllDeviceFlow() {
        allDeviceContentsFlow = flow {
            emit(allDeviceContentList)
        }
    }

    private fun refreshMyDeviceFlow() {
        myDeviceContentsFlow = flow {
            emit(myDeviceContentsList)
        }
    }

    override fun deviceContentDataSource(): List<DeviceContent> = allDeviceContentList

    override fun getAllDevices(): Flow<List<DeviceContent>> = allDeviceContentsFlow

    override suspend fun insertAllDevices(deviceContentList: List<DeviceContent>?) {
        allDeviceContentList.addAll(deviceContentList!!)
        refreshAllDeviceFlow()
    }

    override suspend fun updateDevice(deviceContent: DeviceContent?) {
        allDeviceContentList[0] = deviceContent!!
        refreshAllDeviceFlow()
    }

    override fun getMyDevices(isFavorite: Boolean): Flow<List<MyDeviceContent>> =
        myDeviceContentsFlow

    override suspend fun insertToMyDevice(myDeviceContent: MyDeviceContent?) {
        myDeviceContentsList.add(0, myDeviceContent!!)
        refreshMyDeviceFlow()
    }

    override suspend fun deleteDevice(myDeviceContent: MyDeviceContent?) {
        myDeviceContentsList.removeAt(0)
        refreshMyDeviceFlow()
    }
}
