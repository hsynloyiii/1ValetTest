package com.example.a1valettest.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentMyDevicesBinding
import com.example.a1valettest.view.adapter.MyDevicesAdapter
import com.example.a1valettest.viewmodel.DeviceDatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDevicesFragment : Fragment() {

    private lateinit var binding: FragmentMyDevicesBinding
    private val deviceDatabaseViewModel by activityViewModels<DeviceDatabaseViewModel>()

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

        getMyDevices()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
    }

    private fun getMyDevices() {
        myDevicesAdapter = MyDevicesAdapter()

        binding.recyclerViewFragmentMyDevices.apply {
            adapter = myDevicesAdapter
        }

        deviceDatabaseViewModel.getDevices.observe(viewLifecycleOwner) {
            myDevicesAdapter.differMyDeviceContent.apply {
                submitList(it)

                if (currentList.isNotEmpty())
                    binding.textEmptyMyDeviceList.visibility = GONE
            }
        }

    }


    private fun handleToolbar() {
        binding.toolbarFragmentMyDevices.setNavigationOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.mainDrawerLayout)?.open()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        getMyDevices()
//    }

}