package com.example.a1valettest.utils.module

import com.example.a1valettest.repository.DeviceDatabaseRepository
import com.example.a1valettest.utils.database.DeviceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DeviceDatabaseModule {

    @Provides
    fun provideDeviceDatabaseRepository(deviceDao: DeviceDao): DeviceDatabaseRepository =
        DeviceDatabaseRepository(deviceDao = deviceDao)

}