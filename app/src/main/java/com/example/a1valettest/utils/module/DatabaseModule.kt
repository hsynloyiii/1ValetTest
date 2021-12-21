package com.example.a1valettest.utils.module

import android.content.Context
import com.example.a1valettest.utils.database.DeviceDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Provides
    fun provideDeviceDatabase(@ApplicationContext context: Context): DeviceDataBase =
        DeviceDataBase.getDatabase(context = context)


}