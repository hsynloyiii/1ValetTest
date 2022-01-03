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
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.a1valettest.utils.base.BaseFragment
import com.example.a1valettest.EspressoIdlingResource
import com.example.a1valettest.viewmodel.DeviceDatabaseViewModel
import com.google.android.material.transition.platform.MaterialElevationScale
import com.google.android.material.transition.platform.MaterialFadeThrough
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment @Inject constructor(
    private val homeAdapter: HomeAdapter
) : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val deviceDatabaseViewModel by viewModels<DeviceDatabaseViewModel>()

    private lateinit var newDeviceContentList: MutableList<DeviceContent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.material_motion_duration_long_1).toLong()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        // call the data source just for first launching page (if it is null of empty our data source will be inserted to room db)
        deviceDatabaseViewModel.getDeviceDataSource()

        handleToolbar()
        getDevices()
    }

    private fun getDevices() {
        homeAdapter.setOnItemClickListener { deviceContent, view ->
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.material_motion_duration_long_1).toLong()
            }
            enterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(R.integer.material_motion_duration_long_1).toLong()
            }

            val extras =
                FragmentNavigatorExtras(view to deviceContent.title)
            val action = HomeFragmentDirections
                .actionHomeFragmentToDeviceDetailFragment(deviceContent = deviceContent)

            findNavController().navigate(
                action,
                extras
            )
        }

        binding.recyclerViewFragmentHome.adapter = homeAdapter

        EspressoIdlingResource.increment()
        viewLifecycleOwner.lifecycleScope.launch {
            deviceDatabaseViewModel.getAllDevices
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    newDeviceContentList = it.toMutableList()
                    homeAdapter.submitList(newDeviceContentList)
                    EspressoIdlingResource.decrement()
                }
        }
    }

    private fun handleToolbar() = binding.toolbarFragmentHome.apply {
        setNavigationOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.mainDrawerLayout)?.open()
        }

        val menuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView

        searchView.apply {
            queryHint = "Search all devices"

            binding.run {
                setOnSearchClickListener {
                    appBarLayoutFragmentHome.setExpanded(false, true)
                    startAnimation(
                        AnimationUtils.loadAnimation(context, R.anim.fade_in)
                            .apply { setAnimationListener(null) }
                    )
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
        homeAdapter.submitList(newList)
    }
}