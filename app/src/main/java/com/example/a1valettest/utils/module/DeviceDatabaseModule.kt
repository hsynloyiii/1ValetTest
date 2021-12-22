package com.example.a1valettest.utils.module

import com.example.a1valettest.repository.DeviceDatabaseRepository
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DeviceDatabaseModule {

    @Provides
    fun provideDeviceDatabaseRepository(
        deviceDao: DeviceDao,
        deviceDataBase: DeviceDataBase
    ): DeviceDatabaseRepository =
        DeviceDatabaseRepository(deviceDao = deviceDao, deviceDataBase = deviceDataBase)

}