package com.example.a1valettest.utils.di

import com.example.a1valettest.repository.DeviceDatabaseRepository
import com.example.a1valettest.repository.Repository
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import com.example.a1valettest.utils.di.module.DatabaseModule
import com.example.a1valettest.utils.di.module.RepositoryModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
class FakeRepositoryModule {

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