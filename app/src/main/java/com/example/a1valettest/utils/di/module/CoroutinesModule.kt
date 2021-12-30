package com.example.a1valettest.utils.di.module

import com.example.a1valettest.utils.di.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesModule {

    //--------------------------------------- Dispatchers ----------------------------------------->
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



    //----------------------------------------- Scopes -------------------------------------------->
    @Singleton
    @ApplicationScope
    @Provides
    fun provideCoroutineScope(
        @MainDispatchers mainDispatchers: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainDispatchers)

}