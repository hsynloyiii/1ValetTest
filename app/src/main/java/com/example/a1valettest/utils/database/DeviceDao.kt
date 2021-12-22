package com.example.a1valettest.utils.database

import androidx.room.*
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.DeviceResponse
import com.example.a1valettest.model.MyDeviceContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
interface DeviceDao {

    @Query("SELECT * FROM deviceContent")
    fun getAllDevices(): Flow<List<DeviceContent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDevices(deviceContentList: List<DeviceContent>?)

    @Update
    suspend fun updateDeviceContent(deviceContent: DeviceContent?)

    @Query("SELECT * FROM myDeviceContent WHERE isFavorite = :isFavorite")
    fun getAllMyDevices(isFavorite: Boolean): Flow<List<MyDeviceContent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToMyDevice(myDeviceContent: MyDeviceContent?)

    @Delete
    suspend fun deleteDevice(myDeviceContent: MyDeviceContent?)

}