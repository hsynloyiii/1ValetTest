package com.example.a1valettest.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentDeviceDetailBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.utils.alert
import com.example.a1valettest.utils.convertToMyDeviceContent
import com.example.a1valettest.utils.snackBar
import com.example.a1valettest.viewmodel.DeviceDatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceDetailFragment : Fragment() {

    private lateinit var binding: FragmentDeviceDetailBinding

    private val args by navArgs<DeviceDetailFragmentArgs>()
    private val deviceDatabaseViewModel by activityViewModels<DeviceDatabaseViewModel>()

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolbar()
    }

    private fun handleToolbar() {
        binding.toolbarFragmentDeviceDetail.apply {

            setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.favoriteDevice -> {
                        val favoriteDevice = menu.findItem(R.id.favoriteDevice)
                        if (!deviceContent.isFavorite) {
                            deviceContent.isFavorite = true


                            deviceDatabaseViewModel.insertToMyDevice(
                                myDeviceContent = deviceContent.convertToMyDeviceContent()
                            )

                            updateDeviceContent(deviceContent = deviceContent)

                            binding.root.snackBar(msg = "It successfully added to your list")

                            favoriteDevice.icon = ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_round_star_24,
                                null
                            )
                        } else {
                            context?.alert(
                                title = "Are you sure ?",
                                message = "this action will remove the current device from your favorite devices",
                                positiveButtonText = "Remove",
                                positiveButtonAction = { dialog ->

                                    deviceContent.isFavorite = false

                                    binding.root.snackBar(msg = "It successfully removed from your list")

                                    updateDeviceContent(deviceContent = deviceContent)

                                    deviceDatabaseViewModel.deleteDevice(
                                        myDeviceContent = deviceContent.convertToMyDeviceContent()
                                    )

                                    favoriteDevice.icon = ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.ic_round_star_outline_24,
                                        null
                                    )

                                    dialog.cancel()
                                }
                            )
                        }
                        true
                    }
                    else -> false
                }
            }

            menu.findItem(R.id.favoriteDevice).icon =
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

    private fun updateDeviceContent(deviceContent: DeviceContent) =
        deviceDatabaseViewModel.updateDeviceContent(deviceContent = deviceContent)

}

