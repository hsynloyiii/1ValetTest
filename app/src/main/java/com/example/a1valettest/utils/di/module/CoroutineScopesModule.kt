package com.example.a1valettest.utils.di.module

import com.example.a1valettest.utils.di.ApplicationScope
import com.example.a1valettest.utils.di.MainDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutineScopesModule {

    @Singleton
    @ApplicationScope
    @Provides
    fun provideCoroutineScope(
        @MainDispatchers mainDispatchers: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainDispatchers)

}