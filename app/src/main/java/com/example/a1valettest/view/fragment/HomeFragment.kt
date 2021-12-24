package com.example.a1valettest.view.fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.a1valettest.R
import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.databinding.FragmentHomeBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import android.annotation.SuppressLint
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import androidx.core.view.forEach
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var homeAdapter: HomeAdapter

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var newDeviceContentList: MutableList<DeviceContent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        WindowCompat.setDecorFitsSystemWindows(activity?.window!!, false)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

//        exitTransition = MaterialFadeThrough()
//        allowReturnTransitionOverlap

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        getDevices()

    }

    private fun getDevices() {
        binding.recyclerViewFragmentHome.apply {
            adapter = homeAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {

            homeViewModel.getAllDevices
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    newDeviceContentList = it.toMutableList()
                    homeAdapter.differDeviceContent.submitList(newDeviceContentList)
                }
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
            if (it.title.lowercase(Locale.getDefault()).contains(text))
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