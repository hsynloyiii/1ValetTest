package com.example.a1valettest.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.example.a1valettest.EspressoIdlingResource
import com.example.a1valettest.utils.di.AppEntryPoint
import com.example.a1valettest.utils.extensions.customSetupWithNavController
import com.example.a1valettest.view.fragment.factory.MainFragmentFactory
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    @Inject
    lateinit var mainFragmentFactory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = EntryPointAccessors.fromActivity(
            this,
            AppEntryPoint::class.java
        )
        supportFragmentManager.fragmentFactory = entryPoint.getMainFragmentFactory()

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.navController

        handleNavigationView()
    }


    // As setUpWithNavController in NavigationView is a little bit laggy, I handled it manually
    private fun handleNavigationView() = binding.mainNavigationView.customSetupWithNavController(
        drawerLayout = binding.mainDrawerLayout,
        navController = navController
    )


}