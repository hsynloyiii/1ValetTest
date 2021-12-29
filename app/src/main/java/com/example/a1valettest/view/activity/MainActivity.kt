package com.example.a1valettest.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.utils.di.MainFragmentFactoryEntryPoint
import com.example.a1valettest.view.fragment.factory.MainFragmentFactory
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @Inject
    lateinit var mainFragmentFactory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = EntryPointAccessors.fromActivity(
            this,
            MainFragmentFactoryEntryPoint::class.java
        )
        supportFragmentManager.fragmentFactory = entryPoint.getMainFragmentFactory()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        lifecycleScope.launch {
            dataStoreManager.readFromDataStore().collect {
                when (it.isNight) {
                    true -> setDefaultNightMode(MODE_NIGHT_YES)
                    false -> setDefaultNightMode(MODE_NIGHT_NO)
                    null -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.navController

        handleNavigationView()
    }


    // As setUpWithNavController in NavigationView is a little bit laggy, I handled it manually
    private fun handleNavigationView() {
        binding.mainNavigationView.apply {
            setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.homeFragment -> {
                        navigationItemClick(
                            item = it,
                            resId = R.id.homeFragment,
                            navView = this
                        )
                    }
                    R.id.myDevicesFragment -> {
                        navigationItemClick(
                            item = it,
                            resId = R.id.myDevicesFragment,
                            navView = this
                        )
                    }
                    R.id.settingFragment -> {
                        navigationItemClick(
                            item = it,
                            resId = R.id.settingFragment,
                            navView = this
                        )
                    }
                    else -> false
                }
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment -> setCheckedItem(R.id.homeFragment)
                    R.id.myDevicesFragment -> setCheckedItem(R.id.myDevicesFragment)
                    R.id.settingFragment -> setCheckedItem(R.id.settingFragment)
                }
            }
        }
    }

    private fun navigationItemClick(
        item: MenuItem,
        @IdRes resId: Int,
        navView: NavigationView
    ): Boolean {
        if (item.isChecked)
            closeDrawer()
        else {
            navigate(resId = resId)
            navView.setCheckedItem(resId)
        }
        return true
    }

    private fun navigate(@IdRes resId: Int) {
        if (resId == R.id.homeFragment) {
            navController.navigate(
                R.id.action_to_homeFragment
            )
        } else {
            navController.navigate(resId, args = null, navOptions = navOptions {
                anim {
                    enter = R.anim.slide_in_right
                    popEnter = R.anim.scale_in
                    popExit = R.anim.fade_out
                }
            })
        }
        lifecycleScope.launch {
            delay(300)
            closeDrawer()
        }
    }

    private fun closeDrawer() = binding.mainDrawerLayout.closeDrawer(GravityCompat.START, true)

//    @RequiresApi(Build.VERSION_CODES.R)
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
//            Configuration.UI_MODE_NIGHT_YES -> {
////                setDefaultNightMode(MODE_NIGHT_YES)
//                window.insetsController?.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
//            }
//            Configuration.UI_MODE_NIGHT_NO -> {
////                setDefaultNightMode(MODE_NIGHT_NO)
//            }
//        }
//    }
}