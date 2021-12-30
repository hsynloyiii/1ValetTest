package com.example.a1valettest.view.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentSettingBinding
import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.utils.base.BaseFragment
import com.example.a1valettest.utils.singleChoiceAlert
import com.example.a1valettest.viewmodel.ThemeViewModel
import com.google.android.material.transition.platform.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingFragment @Inject constructor() :
    BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val themeViewModel by viewModels<ThemeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(false)
        enterTransition = MaterialElevationScale(true)
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
     * As app must change the theme mode synchronously as soon as app launches, we don't wanna
     * waste any time to collect from dataStore so I change the UI synchronously with sharedPreferences
     * There is no need to use DataStore here :)
     */
    private fun handleChangeTheme() {
        binding.linearThemeModeFragmentSetting.setOnClickListener {
            context?.singleChoiceAlert(
                title = resources.getString(R.string.chooseTheme),
                listItems = resources.getStringArray(R.array.theme_item),
                checkedItem = themeViewModel.readItemPosition(),
                onItemClickListener = { dialogInterface, i ->
//                    themeViewModel.writeToThemeDataStore(
//                        nightModeByPosition = nightWithItemSelect(selectedItemPosition = i),
//                        selectedThemeItemPosition = i
//                    )

                    context?.getSharedPreferences("NightTheme", MODE_PRIVATE)?.edit {
                        putInt("nightModeByPosition", nightWithItemSelect(selectedItemPosition = i))
                    }
                    dialogInterface.dismiss()
                }
            )
        }
    }

    private fun nightWithItemSelect(selectedItemPosition: Int): Int = when (selectedItemPosition) {
        0 -> 0
        1 -> 1
        2 -> 2
        else -> 2
    }
}