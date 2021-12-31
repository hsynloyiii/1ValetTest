package com.example.a1valettest.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil


inline fun<reified T: Any> differItemCallBack() = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem == newItem
}