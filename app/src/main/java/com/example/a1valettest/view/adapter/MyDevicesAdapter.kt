package com.example.a1valettest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ItemRecyclerviewMyDevicesBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.utils.convertToDeviceContent
import javax.inject.Inject

class MyDevicesAdapter @Inject constructor() :
    RecyclerView.Adapter<MyDevicesAdapter.ViewHolder>() {

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

    private var onItemClickListener: ((DeviceContent) -> Unit)? = null

    fun setOnItemClickListener(listener: (DeviceContent) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {

            val myDeviceContent = differMyDeviceContent.currentList[position]

            binding.deviceContent = myDeviceContent.convertToDeviceContent()

            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(myDeviceContent.convertToDeviceContent())
                }
            }
        }
    }

    override fun getItemCount(): Int = differMyDeviceContent.currentList.size

}