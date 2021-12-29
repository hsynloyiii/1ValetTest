package com.example.a1valettest.utils.di.module

import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.utils.ApplicationContainer
import com.example.a1valettest.utils.di.ApplicationScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationContainer(
        @ApplicationScope applicationScope: CoroutineScope,
        dataStoreManager: DataStoreManager
    ) = ApplicationContainer(
        applicationScope = applicationScope,
        dataStoreManager = dataStoreManager
    )

}