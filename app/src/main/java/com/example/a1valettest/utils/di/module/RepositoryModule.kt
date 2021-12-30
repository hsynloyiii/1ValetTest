package com.example.a1valettest.utils.di.module

import android.content.Context
import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.repository.DeviceDatabaseRepository
import com.example.a1valettest.repository.Repository
import com.example.a1valettest.repository.ThemeRepository
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import com.example.a1valettest.utils.di.IODispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

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

    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context) =
        DataStoreManager(context = context)


    @Singleton
    @Provides
    fun provideThemeRepository(
        dataStoreManager: DataStoreManager
    ) = ThemeRepository(dataStoreManager = dataStoreManager) as Repository.ThemeRepository
}