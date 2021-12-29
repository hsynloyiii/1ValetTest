package com.example.a1valettest.utils.di

import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.view.fragment.factory.MainFragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

// it is for accessing point outside of our graph so we can set our fragmentFactory before onCreate of activity
@EntryPoint
@InstallIn(ActivityComponent::class)
interface AppEntryPoint {
    fun getMainFragmentFactory(): MainFragmentFactory
//    fun getDataStoreManager(): DataStoreManager
}