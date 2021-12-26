package com.example.a1valettest.utils.di

import com.example.a1valettest.repository.DeviceDatabaseRepository
import com.example.a1valettest.repository.Repository
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
abstract class RepositoryModule {

    companion object {

        @Singleton
        @Provides
        fun provideDeviceDatabaseRepository(
            deviceDao: DeviceDao,
            deviceDataBase: DeviceDataBase
        ) =
            DeviceDatabaseRepository(
                deviceDao = deviceDao,
                deviceDataBase = deviceDataBase
            ) as Repository.DeviceDataBaseRepository

    }

}