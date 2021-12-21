package com.example.a1valettest.repository

import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.DeviceResponse
import com.example.a1valettest.utils.MainDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    @MainDispatchers private val mainDispatchers: CoroutineDispatcher
) {

    // We guess that we are fetching from internet so we follow the rest with multi-threading
    suspend fun fetchDeviceContent(): DeviceResponse {
        val devices = withContext(mainDispatchers) {
            buildList {
                add(
                    DeviceContent(
                        id = "1",
                        os = "Android 12",
                        status = "Sold",
                        price = 800,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "",
                        title = "Samsung Galaxy S21",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "2",
                        os = "Android 11",
                        status = "Available",
                        price = 800,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "",
                        title = "Samsung Galaxy A50",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "3",
                        os = "Android 12",
                        status = "Available",
                        price = 800,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "",
                        title = "Samsung Galaxy A12",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "4",
                        os = "Android 12",
                        status = "Available",
                        price = 800,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "",
                        title = "Samsung Galaxy A12",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "5",
                        os = "Android 12",
                        status = "Available",
                        price = 800,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "",
                        title = "Samsung Galaxy A12",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "6",
                        os = "Android 12",
                        status = "Available",
                        price = 800,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "",
                        title = "Samsung Galaxy A12",
                        description = ""
                    )
                )
            }
        }
        return DeviceResponse(devices = devices)
    }


}