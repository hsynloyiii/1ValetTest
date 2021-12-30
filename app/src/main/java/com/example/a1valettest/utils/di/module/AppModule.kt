package com.example.a1valettest.utils.di.module

import android.content.Context
import com.example.a1valettest.repository.DeviceDatabaseRepository
import com.example.a1valettest.repository.Repository
import com.example.a1valettest.utils.ApplicationContainer
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.view.adapter.MyDevicesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// This AppModule provide repositories, adapters or other app classes except Coroutines or Dispatchers
@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    //----------------------------------------- Database ------------------------------------------>
    @Singleton
    @Provides
    fun provideDeviceDatabase(@ApplicationContext context: Context): DeviceDataBase =
        DeviceDataBase.getDatabase(context = context)

    @Singleton
    @Provides
    fun provideDeviceDao(deviceDataBase: DeviceDataBase): DeviceDao = deviceDataBase.deviceDao()



    //--------------------------------------- Repositories ---------------------------------------->
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


    //--------------------------------------- Adapters -------------------------------------------->
    @Singleton
    @Provides
    fun provideHomeAdapter() = HomeAdapter()

    @Singleton
    @Provides
    fun provideMyDeviceAdapter() = MyDevicesAdapter()



    //--------------------------------------- Containers ------------------------------------------>
    @Singleton
    @Provides
    fun provideApplicationContainer(
        @ApplicationContext context: Context
    ) = ApplicationContainer(context = context)

}