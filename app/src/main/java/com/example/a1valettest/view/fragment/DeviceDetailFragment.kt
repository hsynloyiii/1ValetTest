package com.example.a1valettest.view.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.SharedElementCallback
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.a1valettest.R
import com.example.a1valettest.databinding.FragmentDeviceDetailBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.BaseFragment
import com.example.a1valettest.utils.alert
import com.example.a1valettest.utils.convertToMyDeviceContent
import com.example.a1valettest.utils.snackBar
import com.example.a1valettest.viewmodel.DeviceDatabaseViewModel
import com.google.android.material.transition.platform.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DeviceDetailFragment: BaseFragment<FragmentDeviceDetailBinding>(R.layout.fragment_device_detail) {

    private val args by navArgs<DeviceDetailFragmentArgs>()

    private val deviceDatabaseViewModel by viewModels<DeviceDatabaseViewModel>()

    private lateinit var deviceContent: DeviceContent

    override fun FragmentDeviceDetailBinding.initialize() {
        sharedElementEnterTransition = TransitionInflater.from(context!!).inflateTransition(R.transition.item_shared_element)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.deviceContent?.let {
            deviceContent = it
        }
        binding.deviceContent = deviceContent

        binding.imageViewDeviceFragmentDeviceDetail.transitionName = deviceContent.imageUrl

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

                            view?.snackBar(msg = resources.getString(R.string.successfullyAdded))


                            changeFavoriteIcon(
                                item = favoriteDevice,
                                id = R.drawable.ic_round_star_24
                            )

                        } else {
                            context?.alert(
                                title = "Are you sure ?",
                                message = "this action will remove the current device from your favorite devices",
                                positiveButtonText = resources.getString(R.string.remove),
                                positiveButtonAction = { dialog ->

                                    deviceContent.isFavorite = false

                                    updateDeviceContent(deviceContent = deviceContent)

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
                    ContextCompat.getDrawable(context, R.drawable.ic_round_star_24)
                else
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_round_star_outline_24,
                    )

        }
    }

    private fun changeFavoriteIcon(item: MenuItem, @DrawableRes id: Int) =
        ContextCompat.getDrawable(
            context!!,
            id,
        ).also { item.icon = it }

    private fun updateDeviceContent(deviceContent: DeviceContent) =
        deviceDatabaseViewModel.updateDeviceContent(deviceContent = deviceContent)

}

