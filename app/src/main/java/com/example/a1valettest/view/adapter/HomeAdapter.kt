package com.example.a1valettest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ItemRecyclerviewHomeBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.EspressoIdlingResource
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

    private var onItemClickListener: ((DeviceContent) -> Unit)? = null

    fun setOnItemClickListener(listener: (DeviceContent) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.deviceContent = differDeviceContent.currentList[position]

            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(differDeviceContent.currentList[position])
                }
            }
        }
    }

    override fun getItemCount(): Int = differDeviceContent.currentList.size

}