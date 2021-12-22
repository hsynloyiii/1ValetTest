package com.example.a1valettest.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        getDevices()
    }

    private fun getDevices() {
        homeAdapter = HomeAdapter()

        binding.recyclerViewFragmentHome.apply {
            adapter = homeAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.getDeviceContent
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    it.devices?.let { deviceContentList ->
                        homeAdapter.differDeviceContent.submitList(deviceContentList)
                    }
                }
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

//            setOnMenuItemClickListener {
//                when (it.itemId) {
//                    R.id.search -> {
//                        true
//                    }
//                    else -> false
//                }
//            }
        }
    }

    private fun filterDeviceContentList(text: String) {
        val newList = arrayListOf<DeviceContent>()
//        deviceContents.forEach {
//            if (it.title.lowercase(Locale.getDefault()).contains(text))
//                newList.add(it)
//        }
//        homeAdapter.filterList(filteredList = newList)
    }
}