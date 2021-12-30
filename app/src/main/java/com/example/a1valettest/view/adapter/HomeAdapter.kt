package com.example.a1valettest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ItemRecyclerviewHomeBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.EspressoIdlingResource
import javax.inject.Inject

class HomeAdapter @Inject constructor() :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<DeviceContent>() {
        override fun areItemsTheSame(oldItem: DeviceContent, newItem: DeviceContent): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DeviceContent, newItem: DeviceContent): Boolean =
            oldItem == newItem
    }

    private val differDeviceContent = AsyncListDiffer(this, differCallBack)

    fun submitList(list: List<DeviceContent>) {
        EspressoIdlingResource.increment()
        val dataCommitCallback = Runnable {
            EspressoIdlingResource.decrement()
        }
        differDeviceContent.submitList(list, dataCommitCallback)
    }

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

    private var onItemClickListener: ((DeviceContent, View) -> Unit)? = null

    fun setOnItemClickListener(listener: (DeviceContent, View) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val deviceContent = differDeviceContent.currentList[position]
            binding.deviceContent = deviceContent
            itemView.setOnClickListener { view ->
                onItemClickListener?.let {
                    it(deviceContent, binding.cardViewItemRecyclerViewHome)
                }
            }
        }
    }

    override fun getItemCount(): Int = differDeviceContent.currentList.size

}