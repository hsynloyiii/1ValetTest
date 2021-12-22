package com.example.a1valettest.utils.database

import androidx.room.*
import com.example.a1valettest.model.DeviceContent
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Query("SELECT * FROM deviceContent")
    fun getAllDevices(): Flow<List<DeviceContent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(deviceContent: DeviceContent?)

    @Delete
    suspend fun deleteDevice(deviceContent: DeviceContent?)

}