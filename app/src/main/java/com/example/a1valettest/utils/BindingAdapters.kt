package com.example.a1valettest.utils

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@BindingAdapter("imageFromUrl")
fun AppCompatImageView.bindImage(url: String) = Glide
    .with(this.context)
    .load(url)
    .into(this)

@BindingAdapter("customPrice")
fun AppCompatTextView.bindPriceWithSymbol(price: Int) =
    "$price$".also { this.text = it }
