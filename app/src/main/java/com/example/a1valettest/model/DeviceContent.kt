package com.example.a1valettest.model

import android.os.Parcelable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "deviceContent")
data class DeviceContent(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val os: String,
    val status: String,
    val price: Int,
    val currency: String,
    var isFavorite: Boolean,
    val imageUrl: String,
    val title: String,
    val description: String
): Parcelable

@BindingAdapter("imageFromUrl")
fun AppCompatImageView.bindImage(url: String) = Glide
    .with(this.context)
    .load(url)
    .into(this)

@BindingAdapter("customPrice")
fun AppCompatTextView.bindPriceWithSymbol(price: Int) =
    "$price$".also { this.text = it }
