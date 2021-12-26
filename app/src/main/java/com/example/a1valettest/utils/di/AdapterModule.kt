package com.example.a1valettest.utils.di

import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.view.adapter.MyDevicesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Singleton
    @Provides
    fun provideHomeAdapter() = HomeAdapter()

    @Singleton
    @Provides
    fun provideMyDeviceAdapter() = MyDevicesAdapter()

}