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
                        imageUrl = "https://shop.samsung.com/ie/images/products/27514/14920/600x600/SM-G996BZSGEUA.png",
                        title = "Samsung Galaxy S21",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "2",
                        os = "Android 11",
                        status = "Available",
                        price = 700,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "https://toppng.com/uploads/preview/samsung-galaxy-a50-samsung-galaxy-a50-2019-11562961823yplum8mlj9.png",
                        title = "Samsung Galaxy A50",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "3",
                        os = "Android 12",
                        status = "Available",
                        price = 555,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "https://cdn.vodafone.co.uk/en/assets/images/desktop/Samsung_Galaxy_A12_black-full-product-front-600.png",
                        title = "Samsung Galaxy A12",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "4",
                        os = "Android 12",
                        status = "Available",
                        price = 720,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "https://i2.wp.com/mobilize.com.pk/wp-content/uploads/2021/04/mi-11-lite.png?fit=600%2C600&ssl=1",
                        title = "Xiaomi Mi 11",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "5",
                        os = "Android 12",
                        status = "Available",
                        price = 900,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "https://i01.appmifile.com/webfile/globalimg/products/pc/mi-11-ultra/pc-specs-header.png",
                        title = "Xiaomi Mi 11 Ultra",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "6",
                        os = "Android 12",
                        status = "Available",
                        price = 1100,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "https://images.samsung.com/is/image/samsung/p6pim/uk/galaxy-s21/gallery/uk-galaxy-s21-ultra-5g-g988-sm-g998bzsheua-thumb-368888073",
                        title = "Samsung Note 21 Ultra",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "6",
                        os = "Android 12",
                        status = "Available",
                        price = 1100,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "https://images.samsung.com/is/image/samsung/p6pim/uk/galaxy-s21/gallery/uk-galaxy-s21-ultra-5g-g988-sm-g998bzsheua-thumb-368888073",
                        title = "Samsung Note 21 Ultra",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "6",
                        os = "Android 12",
                        status = "Available",
                        price = 1100,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "https://images.samsung.com/is/image/samsung/p6pim/uk/galaxy-s21/gallery/uk-galaxy-s21-ultra-5g-g988-sm-g998bzsheua-thumb-368888073",
                        title = "Samsung Note 21 Ultra",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "6",
                        os = "Android 12",
                        status = "Available",
                        price = 1100,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "https://images.samsung.com/is/image/samsung/p6pim/uk/galaxy-s21/gallery/uk-galaxy-s21-ultra-5g-g988-sm-g998bzsheua-thumb-368888073",
                        title = "Samsung Note 21 Ultra",
                        description = ""
                    )
                )
                add(
                    DeviceContent(
                        id = "6",
                        os = "Android 12",
                        status = "Available",
                        price = 1100,
                        currency = "USD",
                        isFavorite = false,
                        imageUrl = "https://images.samsung.com/is/image/samsung/p6pim/uk/galaxy-s21/gallery/uk-galaxy-s21-ultra-5g-g988-sm-g998bzsheua-thumb-368888073",
                        title = "Samsung Note 21 Ultra",
                        description = ""
                    )
                )
            }
        }
        return DeviceResponse(devices = devices)
    }


}