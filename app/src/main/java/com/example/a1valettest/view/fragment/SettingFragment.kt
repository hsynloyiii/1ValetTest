package com.example.a1valettest.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentSettingBinding
import com.example.a1valettest.model.ThemeUIState
import com.example.a1valettest.repository.DataStoreManager
import com.example.a1valettest.utils.BaseFragment
import com.example.a1valettest.utils.di.IODispatchers
import com.example.a1valettest.utils.di.MainDispatchers
import com.example.a1valettest.utils.singleChoiceAlert
import com.example.a1valettest.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class SettingFragment @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    @IODispatchers private val ioDispatcher: CoroutineDispatcher,
    @MainDispatchers private val mainDispatchers: CoroutineDispatcher
) : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        viewLifecycleOwner.lifecycleScope.launch {
            handleThemeChange()
        }
    }

    private fun handleToolbar() {
        binding.toolbarFragmentSetting.setNavigationOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.mainDrawerLayout)?.open()
        }
    }

    private suspend fun handleThemeChange() {
        dataStoreManager.apply {
            readFromDataStore().collect { themeUIState ->
                withContext(mainDispatchers) {
                    binding.textThemeModeFragmentSetting.setOnClickListener {
                        context.singleChoiceAlert(
                            title = "Choose theme",
                            listItems = resources.getStringArray(R.array.theme_item),
                            checkedItem = themeUIState.selectedThemeItemPosition,
                            onItemClickListener = { dialogInterface, i ->
                                viewLifecycleOwner.lifecycleScope.launch(ioDispatcher) {
                                    writeToDataStore(
                                        ThemeUIState(
                                            isNight = isNightWithItemSelect(selectedItemPosition = i),
                                            selectedThemeItemPosition = i
                                        )
                                    )
                                }
                                dialogInterface.dismiss()
                            }
                        )
                    }
                }
            }
        }
    }

    private fun isNightWithItemSelect(selectedItemPosition: Int): Boolean? {
        return when (selectedItemPosition) {
            0 -> false
            1 -> true
            2 -> null
            else -> null
        }
    }
}