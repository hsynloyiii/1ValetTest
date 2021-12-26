package com.example.a1valettest.utils.database


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
@RunWith(AndroidJUnit4::class)
class DeviceDataBaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var db: DeviceDataBase

    @Inject
    lateinit var dao: DeviceDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun writeAndReadMyDevices() = runTest {
        val myDeviceContent = MyDeviceContent(
            id = "10",
            os = "Android 9",
            status = "Available",
            price = 400,
            currency = "USD",
            isFavorite = true,
            imageUrl = "https://shop.samsung.com/ie/images/products/27514/14920/600x600/SM-G996BZSGEUA.png",
            title = "Samsung Galaxy A20",
            description = "Device Desc",
            company = "Samsung"
        )
        dao.insertToMyDevice(myDeviceContent = myDeviceContent)
        val myDevices = dao.getAllMyDevices(isFavorite = true).first()
        assertThat(myDevices.contains(myDeviceContent)).isTrue()
    }

    @Test
    fun writeAndReadAllDevices() = runTest {
        val deviceContents = listOf(
            DeviceContent(
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
        )
        dao.insertAllDevices(deviceContentList = deviceContents)
        val allDevicesFromDb = dao.getAllDevices().first()

        assertEquals(deviceContents, allDevicesFromDb)
    }

}