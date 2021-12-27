package com.example.a1valettest.utils.di.module

import androidx.fragment.app.Fragment
import com.example.a1valettest.utils.di.FragmentKey
import com.example.a1valettest.view.fragment.HomeFragment
import com.example.a1valettest.view.fragment.MyDevicesFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class FragmentFactoryModule {

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun bindHomeFragment(fragment: HomeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(MyDevicesFragment::class)
    abstract fun bindDeviceFragment(fragment: MyDevicesFragment): Fragment

}