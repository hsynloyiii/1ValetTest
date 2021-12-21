package com.example.a1valettest.model

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


data class DeviceContent(
    val id: String,
    val os: String,
    val status: String,
    val price: Int,
    val currency: String,
    val isFavorite: Boolean,
    val imageUrl: String,
    val title: String,
    val description: String
) {

    companion object {
        @JvmStatic
        @BindingAdapter("url")
        fun bindImage(image: AppCompatImageView, url: String) = Glide
            .with(image.context)
            .load(url)
            .into(image)
    }

}