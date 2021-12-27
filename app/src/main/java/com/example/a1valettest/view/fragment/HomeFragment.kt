package com.example.a1valettest.view.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.a1valettest.R
import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.databinding.FragmentHomeBinding
import com.example.a1valettest.model.DeviceContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import android.annotation.SuppressLint
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.a1valettest.utils.BaseFragment
import com.example.a1valettest.viewmodel.DeviceDatabaseViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment @Inject constructor(
    private val homeAdapter: HomeAdapter
) : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val deviceDatabaseViewModel by viewModels<DeviceDatabaseViewModel>()

    private lateinit var newDeviceContentList: MutableList<DeviceContent>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // we call the data source just for first launching page (if it is null of empty our data source will be inserted to room db)
        deviceDatabaseViewModel.getDeviceDataSource()

        handleToolbar()
        getDevices()
    }

    private fun getDevices() {
        homeAdapter.setOnItemClickListener {
            val action = HomeFragmentDirections
                .actionHomeFragmentToDeviceDetailFragment(deviceContent = it)

            findNavController().navigate(
                action,
                navOptions {
                    anim {
                        enter = R.anim.slide_in_right
                        exit = R.anim.scale_out
                        popEnter = R.anim.scale_in
                        popExit = R.anim.slide_out_right
                    }
                }
            )
        }


        binding.recyclerViewFragmentHome.apply {
            adapter = homeAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {

            deviceDatabaseViewModel.getAllDevices
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    newDeviceContentList = it.toMutableList()
                    homeAdapter.differDeviceContent.submitList(newDeviceContentList)
                }

//                .observe(viewLifecycleOwner) {
//                    newDeviceContentList = it.toMutableList()
//                    homeAdapter.differDeviceContent.submitList(newDeviceContentList)
//                }


        }
    }

    @SuppressLint("SoonBlockedPrivateApi")
    private fun handleToolbar() {
        binding.toolbarFragmentHome.apply {
            setNavigationOnClickListener {
                activity?.findViewById<DrawerLayout>(R.id.mainDrawerLayout)?.open()
            }

            val menuItem = menu.findItem(R.id.search)
            val searchView = menuItem.actionView as SearchView

            searchView.apply {
                queryHint = "Search devices"

                binding.apply {
                    setOnSearchClickListener {
                        appBarLayoutFragmentHome.setExpanded(false, true)
                        //                        val toolbarLayoutParams =
//                            binding.collapsing.layoutParams as AppBarLayout.LayoutParams
//                        toolbarLayoutParams.scrollFlags =
//                            AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
//                        binding.collapsing.layoutParams = toolbarLayoutParams
                        val anim = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                        anim.setAnimationListener(null)
                        menu.forEach {
                            it.actionView.startAnimation(anim)
                        }
                    }

                    setOnQueryTextFocusChangeListener { _, hasFocus ->
                        val fullyExpanded: Boolean =
                            (appBarLayoutFragmentHome.height - appBarLayoutFragmentHome.bottom) == 0
                        if (!hasFocus) {
                            if (fullyExpanded) {
                                appBarLayoutFragmentHome.setExpanded(true, true)
                            }
                        }
                    }
                }

                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        filterDeviceContentList(text = newText!!)
                        return false
                    }
                })
            }
        }
    }

    private fun filterDeviceContentList(text: String) {

        val bindingNoResult = binding.layoutSearchNoResult

        val newList = mutableListOf<DeviceContent>()
        newDeviceContentList.forEach {
            if (it.title.contains(text, ignoreCase = true))
                newList.add(it)
        }
        bindingNoResult.linearNoResult.apply {
            if (newList.isEmpty()) {
                visibility = VISIBLE
                ValueAnimator.ofFloat(0.92f, 1f).apply {
                    duration = 300

                    addUpdateListener {
                        scaleX = it.animatedValue as Float
                        scaleY = it.animatedValue as Float
                    }
                    start()
                }
            } else
                visibility = GONE
        }
        homeAdapter.differDeviceContent.submitList(newList)
    }
}