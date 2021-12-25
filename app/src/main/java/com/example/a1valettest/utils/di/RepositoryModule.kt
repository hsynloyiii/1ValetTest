package com.example.a1valettest.utils.di

import com.example.a1valettest.repository.DeviceDatabaseRepository
import com.example.a1valettest.repository.HomeRepository
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideHomeRepository(
        @MainDispatchers mainDispatchers: CoroutineDispatcher,
        deviceDataBase: DeviceDataBase,
        deviceDao: DeviceDao
    ): HomeRepository =
        HomeRepository(
            mainDispatchers = mainDispatchers,
            deviceDataBase = deviceDataBase,
            deviceDao = deviceDao
        )

    @Provides
    fun provideDeviceDatabaseRepository(
        deviceDao: DeviceDao,
        deviceDataBase: DeviceDataBase
    ): DeviceDatabaseRepository =
        DeviceDatabaseRepository(deviceDao = deviceDao, deviceDataBase = deviceDataBase)

}