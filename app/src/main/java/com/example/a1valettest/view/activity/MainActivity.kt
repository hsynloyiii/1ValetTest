package com.example.a1valettest.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ActivityMainBinding
import androidx.customview.widget.ViewDragHelper
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Field
import android.view.WindowManager

import android.os.Build
import android.view.Window
import androidx.core.view.WindowCompat


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        binding.mainNavigationView.apply {
            setupWithNavController(navController = navHostFragment.navController)

            setCheckedItem(R.id.homeFragment)
        }

    }
}