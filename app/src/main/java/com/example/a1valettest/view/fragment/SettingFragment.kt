package com.example.a1valettest.view.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentSettingBinding
import com.example.a1valettest.utils.base.BaseFragment
import com.example.a1valettest.utils.singleChoiceAlert
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.appcompat.app.AppCompatDelegate.*
import com.google.android.material.transition.platform.MaterialElevationScale
import com.google.android.material.transition.platform.MaterialFadeThrough


@AndroidEntryPoint
class SettingFragment @Inject constructor() :
    BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.material_motion_duration_long_1).toLong()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        handleChangeTheme()
    }

    private fun handleToolbar() {
        binding.toolbarFragmentSetting.setNavigationOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.mainDrawerLayout)?.open()
        }
    }

    /**
     * As app must change the theme mode as soon as it launches synchronously, don't wanna
     * waste any time to collect from dataStore so I change the UI synchronously with sharedPreferences
     * There is no need to use DataStore here :)
     */
    private fun handleChangeTheme() {
        val themeSharedPreferences = context?.getSharedPreferences("NightTheme", MODE_PRIVATE)
        binding.linearThemeModeFragmentSetting.setOnClickListener {
            context?.singleChoiceAlert(
                title = resources.getString(R.string.chooseTheme),
                listItems = resources.getStringArray(R.array.theme_item),
                checkedItem = themeSharedPreferences?.getInt("nightModeByPosition", 2) ?: 2,
                onItemClickListener = { dialogInterface, i ->
                    when (i) {
                        0 -> setDefaultNightMode(MODE_NIGHT_NO)
                        1 -> setDefaultNightMode(MODE_NIGHT_YES)
                        2 -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                    themeSharedPreferences?.edit {
                        putInt("nightModeByPosition", i)
                    }
                    dialogInterface.dismiss()
                }
            )
        }
    }

}