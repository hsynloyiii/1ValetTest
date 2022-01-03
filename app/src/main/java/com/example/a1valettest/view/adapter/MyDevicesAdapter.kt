package com.example.a1valettest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ItemRecyclerviewMyDevicesBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.EspressoIdlingResource
import com.example.a1valettest.utils.extensions.convertToDeviceContent
import com.example.a1valettest.utils.differItemCallBack
import javax.inject.Inject

class MyDevicesAdapter @Inject constructor() :
    ListAdapter<MyDeviceContent, MyDevicesAdapter.ViewHolder>(differItemCallBack<MyDeviceContent>()) {


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

    private var onItemClickListener: ((DeviceContent, View) -> Unit)? = null

    fun setOnItemClickListener(listener: (DeviceContent, View) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {

            val myDeviceContent = getItem(position)

            binding.myDeviceContent = myDeviceContent

            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(
                        myDeviceContent.convertToDeviceContent(),
                        binding.cardViewItemRecyclerViewMyDevices
                    )
                }
            }
        }
    }


}