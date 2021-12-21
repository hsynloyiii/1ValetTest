package com.example.a1valettest.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.a1valettest.R
import com.example.a1valettest.adapter.HomeAdapter
import com.example.a1valettest.databinding.FragmentHomeBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.viewmodel.HomeViewModel
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeAdapter: HomeAdapter

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        enterTransition = MaterialFadeThrough()


        val deviceContents = mutableListOf<DeviceContent>()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.getDeviceContent.collect {
                    deviceContents.addAll(it.devices)
                }
            }
        }

        homeAdapter = HomeAdapter(deviceContentList = deviceContents)

        binding.recyclerViewFragmentHome.apply {
            adapter = homeAdapter
        }



        return binding.root
    }

}