package com.example.a1valettest.utils.di

import com.example.a1valettest.view.fragment.factory.MainFragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface MainFragmentFactoryEntryPoint {
    fun getMainFragmentFactory(): MainFragmentFactory
}