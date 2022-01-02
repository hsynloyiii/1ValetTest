package com.example.a1valettest.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val description: String,
    val company: String
) : Parcelable