package com.example.a1valettest.view.fragment

import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentSettingBinding
import com.example.a1valettest.model.ThemeUIState
import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.utils.BaseFragment
import com.example.a1valettest.utils.di.IODispatchers
import com.example.a1valettest.utils.singleChoiceAlert
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SettingFragment @Inject constructor(
    @IODispatchers private val ioDispatcher: CoroutineDispatcher,
    private val dataStoreManager: DataStoreManager
) : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private var position : Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        viewLifecycleOwner.lifecycleScope.launch {
            handleThemeChange()
        }
        binding.textThemeModeFragmentSetting.setOnClickListener {
            context?.singleChoiceAlert(
                title = "Choose theme",
                listItems = resources.getStringArray(R.array.theme_item),
                checkedItem = position ?: 2,
                onItemClickListener = { dialogInterface, i ->
                    viewLifecycleOwner.lifecycleScope.launch(ioDispatcher) {
                        dataStoreManager.writeToDataStore(
                            ThemeUIState(
                                nightModeByPosition = nightWithItemSelect(selectedItemPosition = i),
                                selectedThemeItemPosition = i
                            )
                        )
                    }
                    dialogInterface.dismiss()
                }
            )
        }
    }

    private fun handleToolbar() {
        binding.toolbarFragmentSetting.setNavigationOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.mainDrawerLayout)?.open()
        }
    }

    private suspend fun handleThemeChange() {
        dataStoreManager.readFromDataStore().collect { themeUIState ->
            position = themeUIState.selectedThemeItemPosition
        }
    }


    private fun nightWithItemSelect(selectedItemPosition: Int): Int {
        return when (selectedItemPosition) {
            0 -> 0
            1 -> 1
            2 -> 2
            else -> 2
        }
    }
}