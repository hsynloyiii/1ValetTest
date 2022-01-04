package com.example.a1valettest.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentDeviceDetailBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.base.BaseFragment
import com.example.a1valettest.utils.extensions.alert
import com.example.a1valettest.utils.extensions.convertToMyDeviceContent
import com.example.a1valettest.utils.extensions.snackBar
import com.example.a1valettest.viewmodel.DeviceDatabaseViewModel
import com.google.android.material.transition.platform.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceDetailFragment :
    BaseFragment<FragmentDeviceDetailBinding>(R.layout.fragment_device_detail) {

    private val args by navArgs<DeviceDetailFragmentArgs>()

    private val deviceDatabaseViewModel by viewModels<DeviceDatabaseViewModel>()

    private lateinit var deviceContent: DeviceContent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            duration = resources.getInteger(R.integer.material_motion_duration_long_1).toLong()
            scrimColor = Color.TRANSPARENT
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.deviceContent?.let { deviceContent = it }
        binding.deviceContent = deviceContent

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
                            updateDeviceContent(setFavorite = true, deviceContent = deviceContent)

                            deviceDatabaseViewModel.insertToMyDevice(
                                myDeviceContent = deviceContent.convertToMyDeviceContent()
                            )

                            changeFavoriteIcon(
                                item = favoriteDevice,
                                id = R.drawable.ic_round_star_24
                            )

                            view?.snackBar(msg = resources.getString(R.string.successfullyAdded))

                        } else {
                            context?.alert(
                                title = "Are you sure ?",
                                message = "this action will remove the current device from your favorite devices",
                                positiveButtonText = resources.getString(R.string.remove),
                                positiveButtonAction = { dialog ->

                                    updateDeviceContent(
                                        setFavorite = false,
                                        deviceContent = deviceContent
                                    )

                                    deviceDatabaseViewModel.deleteDevice(
                                        myDeviceContent = deviceContent.convertToMyDeviceContent()
                                    )

                                    view?.snackBar(msg = resources.getString(R.string.successfullyRemoved))

                                    changeFavoriteIcon(
                                        item = favoriteDevice,
                                        id = R.drawable.ic_round_star_outline_24
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
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_round_star_24
                    )
                else
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_round_star_outline_24
                    )

        }
    }

    private fun changeFavoriteIcon(item: MenuItem, @DrawableRes id: Int) =
        ContextCompat.getDrawable(
            requireContext(),
            id
        ).also { item.icon = it }

    private fun updateDeviceContent(setFavorite: Boolean, deviceContent: DeviceContent) {
        deviceContent.isFavorite = setFavorite
        deviceDatabaseViewModel.updateDeviceContent(deviceContent = deviceContent)
    }

}

