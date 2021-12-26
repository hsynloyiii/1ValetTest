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
import com.example.a1valettest.databinding.ItemRecyclerviewHomeBinding
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.interfaces.OnClick
import com.example.a1valettest.view.fragment.HomeFragmentDirections
import javax.inject.Inject

class HomeAdapter(
    private val onItemClick: (DeviceContent) -> Unit
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<DeviceContent>() {
        override fun areItemsTheSame(oldItem: DeviceContent, newItem: DeviceContent): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DeviceContent, newItem: DeviceContent): Boolean =
            oldItem == newItem
    }

    val differDeviceContent = AsyncListDiffer(this, differCallBack)

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            deviceContent = differDeviceContent.currentList[position]

            materialContainerAllDevices.setOnClickListener {
                onItemClick(differDeviceContent.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int = differDeviceContent.currentList.size

//    override fun navigateToDetail(
//        view: View,
//        deviceContent: DeviceContent
//    ) {
//        val action = HomeFragmentDirections
//            .actionHomeFragmentToDeviceDetailFragment(deviceContent = deviceContent)
//
//        findNavController(view).navigate(
//            action,
//            navOptions {
//                anim {
//                    enter = R.anim.slide_in_right
//                    exit = R.anim.scale_out
//                    popEnter = R.anim.scale_in
//                    popExit = R.anim.slide_out_right
//                }
//            }
//        )
//    }


}