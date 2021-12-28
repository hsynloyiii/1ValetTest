package com.example.a1valettest.model

import com.example.a1valettest.utils.convertToDeviceContent
import com.example.a1valettest.utils.convertToMyDeviceContent
import org.junit.Assert.*
import org.junit.Test

// Here we test our data classes conversion with same data
class DeviceModelTest {

    private lateinit var deviceContent: DeviceContent
    private lateinit var myDeviceContent: MyDeviceContent

    @Test
    fun convertDeviceContentToMyDeviceContent() {
        deviceContent = DeviceContent(
            id = "1",
            os = "Android 12",
            status = "Sold",
            price = 800,
            currency = "USD",
            isFavorite = false,
            imageUrl = "https://shop.samsung.com/ie/images/products/27514/14920/600x600/SM-G996BZSGEUA.png",
            title = "Samsung Galaxy S21",
            description = "The Samsung Galaxy S21+ 5G comes with a 6.7 inch touchscreen with 394PPI. It packs a 64-megapixel rear camera and a 10-megapixel selfie-camera. This is all powered by the Exynos 2100 International chipset and 8GB of RAM.",
            company = "Samsung"
        )

        myDeviceContent = deviceContent.convertToMyDeviceContent()

        assertEquals(myDeviceContent.convertToDeviceContent(), deviceContent)
    }

    @Test
    fun convertMyDeviceContentToDeviceContent() {
        myDeviceContent = MyDeviceContent(
            id = "1",
            os = "Android 12",
            status = "Sold",
            price = 800,
            currency = "USD",
            isFavorite = false,
            imageUrl = "https://shop.samsung.com/ie/images/products/27514/14920/600x600/SM-G996BZSGEUA.png",
            title = "Samsung Galaxy S21",
            description = "The Samsung Galaxy S21+ 5G comes with a 6.7 inch touchscreen with 394PPI. It packs a 64-megapixel rear camera and a 10-megapixel selfie-camera. This is all powered by the Exynos 2100 International chipset and 8GB of RAM.",
            company = "Samsung"
        )

        deviceContent = myDeviceContent.convertToDeviceContent()

        assertEquals(deviceContent.convertToMyDeviceContent(), myDeviceContent)
    }

}