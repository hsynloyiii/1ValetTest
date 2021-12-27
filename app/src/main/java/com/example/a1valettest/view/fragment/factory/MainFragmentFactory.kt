package com.example.a1valettest.view.fragment.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.view.adapter.MyDevicesAdapter
import com.example.a1valettest.view.fragment.HomeFragment
import com.example.a1valettest.view.fragment.MyDevicesFragment
import javax.inject.Inject
import javax.inject.Provider

class MainFragmentFactory @Inject constructor(
    private val provideMap: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {

    // As specified map key for fragment now we won't manually instantiate them here
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        val creator = provideMap[fragmentClass] ?: provideMap.entries.firstOrNull {
            fragmentClass.isAssignableFrom(it.key)
        }?.value

        return creator?.get() ?: super.instantiate(classLoader, className)
    }

}