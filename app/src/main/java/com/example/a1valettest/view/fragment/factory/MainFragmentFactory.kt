package com.example.a1valettest.view.fragment.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.view.adapter.MyDevicesAdapter
import com.example.a1valettest.view.fragment.HomeFragment
import com.example.a1valettest.view.fragment.MyDevicesFragment
import javax.inject.Inject

class MainFragmentFactory @Inject constructor(
    private val homeAdapter: HomeAdapter,
    private val myDevicesAdapter: MyDevicesAdapter,
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            HomeFragment::class.java.name -> HomeFragment(homeAdapter = homeAdapter)
            MyDevicesFragment::class.java.name -> MyDevicesFragment(myDevicesAdapter = myDevicesAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }

}