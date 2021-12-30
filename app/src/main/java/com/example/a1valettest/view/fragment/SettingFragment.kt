package com.example.a1valettest.view.fragment

import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentSettingBinding
import com.example.a1valettest.model.ThemeUIState
import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.utils.BaseFragment
import com.example.a1valettest.utils.singleChoiceAlert
import com.example.a1valettest.viewmodel.ThemeViewModel
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SettingFragment @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val themeViewModel by viewModels<ThemeViewModel>()

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

    private fun handleChangeTheme() {
        binding.textThemeModeFragmentSetting.setOnClickListener {
            context?.singleChoiceAlert(
                title = "Choose theme",
                listItems = resources.getStringArray(R.array.theme_item),
                checkedItem = themeViewModel.readItemPosition(),
                onItemClickListener = { dialogInterface, i ->
                    themeViewModel.writeToThemeDataStore(
                        nightModeByPosition = nightWithItemSelect(selectedItemPosition = i),
                        selectedThemeItemPosition = i
                    )
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