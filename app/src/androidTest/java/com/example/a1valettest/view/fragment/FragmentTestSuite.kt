package com.example.a1valettest.view.fragment

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    HomeFragmentTest::class,
    MyDevicesFragmentTest::class,
    DeviceDetailFragmentTest::class
)
class FragmentTestSuite