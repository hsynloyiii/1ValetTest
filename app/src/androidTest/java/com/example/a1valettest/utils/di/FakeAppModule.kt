package com.example.a1valettest.utils.di

import android.content.Context
import com.example.a1valettest.repository.DeviceDatabaseRepository
import com.example.a1valettest.repository.Repository
import com.example.a1valettest.utils.ApplicationContainer
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import com.example.a1valettest.utils.di.module.AppModule
import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.view.adapter.MyDevicesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object FakeAppModule {

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