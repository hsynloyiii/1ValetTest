package com.example.a1valettest.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentDeviceDetailBinding


class DeviceDetailFragment : Fragment() {

    private lateinit var binding: FragmentDeviceDetailBinding

    private val args by navArgs<DeviceDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_device_detail,
            container,
            false
        )

        binding.textViewTest.text = args.deviceName
        binding.textViewTest.setOnClickListener {
            val action = DeviceDetailFragmentDirections
                .actionDeviceDetailFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }


}