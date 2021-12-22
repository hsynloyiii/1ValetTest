package com.example.a1valettest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.a1valettest.R
import com.example.a1valettest.databinding.ItemRecyclerviewHomeBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.interfaces.OnClick
import com.example.a1valettest.view.fragment.HomeFragmentDirections

class HomeAdapter(private val deviceContentList: List<DeviceContent>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>(), OnClick.HomeAdapter {

    inner class ViewHolder(val binding: ItemRecyclerviewHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_recyclerview_home,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.binding.deviceContent = deviceContentList[position]
        holder.binding.onClick = this
    }

    override fun getItemCount(): Int = deviceContentList.size

    override fun navigateToDetail(view: View, deviceContent: DeviceContent) {
        val action = HomeFragmentDirections
            .actionHomeFragmentToDeviceDetailFragment(deviceContent = deviceContent)

        findNavController(view).navigate(action)
    }
}