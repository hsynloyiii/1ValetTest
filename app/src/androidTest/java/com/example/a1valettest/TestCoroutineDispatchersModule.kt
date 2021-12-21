package com.example.a1valettest

import android.os.AsyncTask
import com.example.a1valettest.utils.DefaultDispatchers
import com.example.a1valettest.utils.IODispatchers
import com.example.a1valettest.utils.MainDispatchers
import com.example.a1valettest.utils.module.CoroutineDispatchersModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutineDispatchersModule::class]
)
@Module
object TestCoroutineDispatchersModule {

    @DefaultDispatchers
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher =
        AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

    @IODispatchers
    @Provides
    fun provideIOtDispatcher(): CoroutineDispatcher =
        AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

    @MainDispatchers
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher =
        Dispatchers.Main

}