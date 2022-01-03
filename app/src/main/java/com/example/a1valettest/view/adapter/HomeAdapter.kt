package com.example.a1valettest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ItemRecyclerviewHomeBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.differItemCallBack
import javax.inject.Inject

class HomeAdapter @Inject constructor() :
    ListAdapter<DeviceContent, HomeAdapter.ViewHolder>(differItemCallBack<DeviceContent>()) {

    inner class ViewHolder(val binding: ItemRecyclerviewHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_recyclerview_home,
                parent,
                false
            )
        )

    // Item click callback
    private var onItemClickListener: ((DeviceContent, View) -> Unit)? = null
    fun setOnItemClickListener(listener: (DeviceContent, View) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val deviceContent = getItem(position)
            binding.deviceContent = deviceContent
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(deviceContent, binding.cardViewItemRecyclerViewHome)
                }
            }
        }
    }

}