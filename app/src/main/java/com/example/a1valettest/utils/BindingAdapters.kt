package com.example.a1valettest.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageFromUrl")
fun AppCompatImageView.bindImage(url: String) = Glide
    .with(this.context)
    .load(url)
    .into(this)

@BindingAdapter("customPrice")
fun AppCompatTextView.bindPriceWithSymbol(price: Int) =
    "$price$".also { this.text = it }
