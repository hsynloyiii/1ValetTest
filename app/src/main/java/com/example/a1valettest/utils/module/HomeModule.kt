package com.example.a1valettest.utils.module

import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.repository.HomeRepository
import com.example.a1valettest.utils.MainDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Singleton
    @Provides
    fun provideHomeRepository(
        @MainDispatchers mainDispatchers: CoroutineDispatcher
    ): HomeRepository =
        HomeRepository(mainDispatchers = mainDispatchers)

}