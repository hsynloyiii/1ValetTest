package com.example.a1valettest.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentMyDevicesBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.toast
import com.example.a1valettest.view.adapter.MyDevicesAdapter
import com.example.a1valettest.viewmodel.DeviceDatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyDevicesFragment : Fragment() {

    private lateinit var binding: FragmentMyDevicesBinding
    private val deviceDatabaseViewModel by viewModels<DeviceDatabaseViewModel>()

    private lateinit var myDevicesAdapter: MyDevicesAdapter

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMyDevices()
        handleToolbar()
    }

    private fun getMyDevices() {
        val myDevicesContent = mutableListOf<DeviceContent>()

        myDevicesAdapter = MyDevicesAdapter(deviceContentList = myDevicesContent)

        binding.recyclerViewFragmentMyDevices.apply {
            adapter = myDevicesAdapter
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                deviceDatabaseViewModel.getDevices.collectLatest { deviceContentList ->
//                    myDevicesContent.clear()
//                    myDevicesContent.addAll(deviceContentList)
//                    Log.i("TestLogForDatabase", myDevicesContent.toString())
//                }
            deviceDatabaseViewModel.getDevices.observe(viewLifecycleOwner) {
                myDevicesContent.clear()
                myDevicesContent.addAll(it)
                Log.i("TestLogForDatabase", myDevicesContent.toString())

                if (myDevicesContent.isNotEmpty())
                    binding.textEmptyMyDeviceList.visibility = GONE
            }
//        }
//        }
    }

    private fun handleToolbar() {
        binding.toolbarFragmentMyDevices.setNavigationOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.mainDrawerLayout)?.open()
        }
    }

    override fun onStart() {
        super.onStart()
        getMyDevices()
    }

    override fun onResume() {
        super.onResume()
        getMyDevices()
    }
}