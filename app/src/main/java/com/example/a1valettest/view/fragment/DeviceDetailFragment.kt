package com.example.a1valettest.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentDeviceDetailBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.toast
import com.example.a1valettest.viewmodel.DeviceDatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DeviceDetailFragment : Fragment() {

    private lateinit var binding: FragmentDeviceDetailBinding

    private val args by navArgs<DeviceDetailFragmentArgs>()
    private val deviceDatabaseViewModel by viewModels<DeviceDatabaseViewModel>()

    private lateinit var deviceContent: DeviceContent

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
        args.deviceContent?.let {
            deviceContent = it
        }

        binding.deviceContent = deviceContent

        deviceContent.isFavorite = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
    }

    private fun handleToolbar() {
        binding.toolbarFragmentDeviceDetail.apply {

            setNavigationOnClickListener {
                val action = DeviceDetailFragmentDirections
                    .actionDeviceDetailFragmentToHomeFragment()
                findNavController().navigate(action)
            }

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.favoriteDevice -> {
                        deviceContent.isFavorite = !deviceContent.isFavorite
                        menu.findItem(R.id.favoriteDevice).apply {
                            icon =
                                if (deviceContent.isFavorite)
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.ic_round_star_24,
                                        null
                                    )
                                else
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.ic_round_star_outline_24,
                                        null
                                    )
                        }
//                        deviceDatabaseViewModel.insertDevice(deviceContent = deviceContent)
                        true
                    }
                    else -> false
                }
            }

            menu.findItem(R.id.favoriteDevice).apply {
                icon =
                    if (args.deviceContent?.isFavorite!!)
                        ResourcesCompat.getDrawable(resources, R.drawable.ic_round_star_24, null)
                    else
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_round_star_outline_24,
                            null
                        )
            }
        }
    }


}