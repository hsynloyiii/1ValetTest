package com.example.a1valettest.utils.di.module

import android.content.Context
import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.utils.ApplicationContainer
import com.example.a1valettest.utils.di.ApplicationScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationContainer(
        @ApplicationContext context: Context,
        @ApplicationScope applicationScope: CoroutineScope,
        dataStoreManager: DataStoreManager
    ) = ApplicationContainer(
        context = context,
        applicationScope = applicationScope,
        dataStoreManager = dataStoreManager
    )

}