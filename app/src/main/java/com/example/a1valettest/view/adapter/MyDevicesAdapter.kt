package com.example.a1valettest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ItemRecyclerviewMyDevicesBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.utils.interfaces.OnClick
import com.example.a1valettest.view.fragment.MyDevicesFragmentDirections

class MyDevicesAdapter :
    RecyclerView.Adapter<MyDevicesAdapter.ViewHolder>(), OnClick.MyDeviceAdapter {

    private val differCallBack = object : DiffUtil.ItemCallback<MyDeviceContent>() {
        override fun areItemsTheSame(oldItem: MyDeviceContent, newItem: MyDeviceContent): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: MyDeviceContent,
            newItem: MyDeviceContent
        ): Boolean =
            oldItem == newItem
    }

    val differMyDeviceContent = AsyncListDiffer(this, differCallBack)

    inner class ViewHolder(val binding: ItemRecyclerviewMyDevicesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recyclerview_my_devices,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

            val myDeviceContent = differMyDeviceContent.currentList[position]

            val deviceContentConverted =
                DeviceContent(
                    myDeviceContent.id,
                    myDeviceContent.os,
                    myDeviceContent.status,
                    myDeviceContent.price,
                    myDeviceContent.currency,
                    myDeviceContent.isFavorite,
                    myDeviceContent.imageUrl,
                    myDeviceContent.title,
                    myDeviceContent.description
                )

            deviceContent = deviceContentConverted
            onClick = this@MyDevicesAdapter
        }
    }

    override fun getItemCount(): Int = differMyDeviceContent.currentList.size

    override fun navigateToDetail(view: View, deviceContent: DeviceContent) {
        val action = MyDevicesFragmentDirections
            .actionMyDevicesFragmentToDeviceDetailFragment(deviceContent = deviceContent)

        findNavController(view).navigate(
            action,
            navOptions {
                anim {
                    enter = R.anim.slide_in_right
                    exit = R.anim.scale_out
                    popEnter = R.anim.scale_in
                    popExit = R.anim.slide_out_right
                }
            }
        )
    }
}