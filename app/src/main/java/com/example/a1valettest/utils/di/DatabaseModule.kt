package com.example.a1valettest.utils.di

import android.content.Context
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Singleton
    @Provides
    fun provideDeviceDatabase(@ApplicationContext context: Context): DeviceDataBase =
        DeviceDataBase.getDatabase(context = context)

    @Singleton
    @Provides
    fun provideDeviceDao(deviceDataBase: DeviceDataBase): DeviceDao = deviceDataBase.deviceDao()

}