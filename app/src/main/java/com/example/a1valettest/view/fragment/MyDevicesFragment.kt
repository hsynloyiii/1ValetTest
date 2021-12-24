package com.example.a1valettest.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentMyDevicesBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.view.adapter.MyDevicesAdapter
import com.example.a1valettest.viewmodel.DeviceDatabaseViewModel
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MyDevicesFragment : Fragment() {

    private lateinit var binding: FragmentMyDevicesBinding

    private val deviceDatabaseViewModel by viewModels<DeviceDatabaseViewModel>()

    @Inject
    lateinit var myDevicesAdapter: MyDevicesAdapter

    private lateinit var newMyDeviceContentList: MutableList<MyDeviceContent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_devices,
            container,
            false
        )

//        enterTransition = MaterialFadeThrough()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
        viewLifecycleOwner.lifecycleScope.launch {
            delay(300)
            getMyDevices()
        }
    }

    private suspend fun getMyDevices() {
        binding.recyclerViewFragmentMyDevices.apply {
            adapter = myDevicesAdapter
        }

        deviceDatabaseViewModel.getDevices
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect {
                newMyDeviceContentList = it.toMutableList()
                myDevicesAdapter.differMyDeviceContent.apply {
                    submitList(newMyDeviceContentList)

                    if (currentList.isNotEmpty())
                        binding.textEmptyMyDeviceList.visibility = GONE
                }
                Log.i("TestMyDeviceFragment", it.toString())
            }


    }


    private fun handleToolbar() {
        binding.toolbarFragmentMyDevices.apply {
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
        val newList = mutableListOf<MyDeviceContent>()
        newMyDeviceContentList.forEach {
            if (it.title.lowercase(Locale.getDefault()).contains(text))
                newList.add(it)
        }
        myDevicesAdapter.differMyDeviceContent.submitList(newList)
    }
}