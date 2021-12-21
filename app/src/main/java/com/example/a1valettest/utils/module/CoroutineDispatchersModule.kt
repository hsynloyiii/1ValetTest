package com.example.a1valettest.utils.module

import com.example.a1valettest.utils.DefaultDispatchers
import com.example.a1valettest.utils.IODispatchers
import com.example.a1valettest.utils.MainDispatchers
import com.example.a1valettest.utils.MainImmediateDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class CoroutineDispatchersModule {

    @DefaultDispatchers
    @Provides
    fun provideDefaultDispatchers(): CoroutineDispatcher = Dispatchers.Default

    @IODispatchers
    @Provides
    fun provideIODispatchers(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatchers
    @Provides
    fun provideMainDispatchers(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatchers
    @Provides
    fun provideMainImmediateDispatchers(): CoroutineDispatcher = Dispatchers.Main.immediate


}