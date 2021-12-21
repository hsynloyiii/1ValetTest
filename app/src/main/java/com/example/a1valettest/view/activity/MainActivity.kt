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

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        binding.mainNavigationView.apply {
            setupWithNavController(navController = navHostFragment.navController)

            setCheckedItem(R.id.homeFragment)
        }




        val mDragger: Field = binding.mainDrawerLayout.javaClass.getDeclaredField(
            "mRightDragger"
        ) //mRightDragger for right obviously

        mDragger.isAccessible = true
        val draggerObj = mDragger
            .get(binding.mainDrawerLayout)

        val mEdgeSize: Field = draggerObj.javaClass.getDeclaredField(
            "mEdgeSize"
        )
        mEdgeSize.isAccessible = true
        val edge: Int = mEdgeSize.getInt(draggerObj)

        mEdgeSize.setInt(
            draggerObj,
            edge * 5
        ) //optimal value as for me, you may set any constant in dp


    }
}