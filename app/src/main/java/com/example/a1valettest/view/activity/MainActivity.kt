package com.example.a1valettest.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

import androidx.core.view.WindowCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.setupWithNavController
import com.example.a1valettest.utils.di.MainFragmentFactoryEntryPoint
import com.example.a1valettest.view.fragment.factory.MainFragmentFactory
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment

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

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        binding.mainNavigationView.apply {
            setCheckedItem(R.id.homeFragment)

            setupWithNavController(navController = navHostFragment.navController)
        }

    }


}