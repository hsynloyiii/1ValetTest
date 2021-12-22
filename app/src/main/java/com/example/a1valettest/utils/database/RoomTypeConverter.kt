package com.example.a1valettest.utils.database

import androidx.room.TypeConverter
import com.example.a1valettest.model.DeviceContent
import org.json.JSONObject

class RoomTypeConverter {

    @TypeConverter
    fun deviceContentToString(deviceContent: DeviceContent): String =
        JSONObject().apply {
            put("id", deviceContent.id)
            put("os", deviceContent.os)
            put("status", deviceContent.status)
            put("price", deviceContent.price)
            put("currency", deviceContent.currency)
            put("isFavorite", deviceContent.isFavorite)
            put("imageUrl", deviceContent.imageUrl)
            put("title", deviceContent.title)
            put("description", deviceContent.description)
        }.toString()

    @TypeConverter
    fun stringToDeviceContent(string: String): DeviceContent {
        val json = JSONObject(string)
        return DeviceContent(
            id = json.getString("id"),
            os = json.getString("os"),
            status = json.getString("status"),
            price = json.getInt("price"),
            currency = json.getString("currency"),
            isFavorite = json.getBoolean("isFavorite"),
            imageUrl = json.getString("imageUrl"),
            title = json.getString("title"),
            description = json.getString("description")
        )
    }

}