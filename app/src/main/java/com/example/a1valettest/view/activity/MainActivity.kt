package com.example.a1valettest.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.example.a1valettest.utils.di.AppEntryPoint
import com.example.a1valettest.utils.extensions.customSetUpWithNavController
import com.example.a1valettest.view.fragment.factory.MainFragmentFactory
import dagger.hilt.android.EntryPointAccessors
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


    // made setUpWithNavController more flexible such as set custom animation
    private fun handleNavigationView() = binding.mainNavigationView.customSetUpWithNavController(
        navController = navController,
        closeDrawerLayoutDelay = 150,
        enterAnim = R.anim.scale_in,
        popEnterAnim = R.anim.scale_in
    )

}