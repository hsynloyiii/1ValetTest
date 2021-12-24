package com.example.a1valettest.view.fragment

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
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

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
        viewLifecycleOwner.lifecycleScope.launch {
            delay(300)
            getDevices()
        }
    }

    private suspend fun getDevices() {
        binding.recyclerViewFragmentHome.apply {
            adapter = homeAdapter
        }

        homeViewModel.getAllDevices
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect {
                newDeviceContentList = it.toMutableList()
                homeAdapter.differDeviceContent.submitList(newDeviceContentList)
            }
    }

    private fun handleToolbar() {
        binding.toolbarFragmentHome.apply {
            setNavigationOnClickListener {
                activity?.findViewById<DrawerLayout>(R.id.mainDrawerLayout)?.open()
            }

            val menuItem = menu.findItem(R.id.search)
            val searchView = menuItem.actionView as SearchView
            searchView.apply {
                queryHint = "Search devices"
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
        val newList = mutableListOf<DeviceContent>()
        newDeviceContentList.forEach {
            if (it.title.lowercase(Locale.getDefault()).contains(text))
                newList.add(it)
        }
        homeAdapter.differDeviceContent.submitList(newList)
    }
}