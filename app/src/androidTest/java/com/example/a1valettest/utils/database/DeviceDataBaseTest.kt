package com.example.a1valettest.utils.database


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.utils.convertToDeviceContent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import javax.inject.Inject
import kotlin.jvm.Throws

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
@RunWith(AndroidJUnit4::class)
class DeviceDataBaseTest {

    // As already created conversion for each data classes no need to define them separately
    private lateinit var myDeviceContent: MyDeviceContent

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

        myDeviceContent = MyDeviceContent(
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
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.clearAllTables()
    }

    @Test
    @Throws(Exception::class)
    fun insertToMyDevices() = runTest {
        dao.insertToMyDevice(myDeviceContent = myDeviceContent)

        val myDevices = dao.getAllMyDevices(isFavorite = true).first()

        assertTrue(myDevices.contains(myDeviceContent))
    }

    @Test
    @Throws(Exception::class)
    fun insertToAllDevices() = runTest {
        val deviceContents = listOf(myDeviceContent.convertToDeviceContent())

        dao.insertAllDevices(deviceContentList = deviceContents)
        val allDevicesFromDb = dao.getAllDevices().first()

        assertEquals(deviceContents, allDevicesFromDb)
    }

    @Test
    @Throws(Exception::class)
    fun deleteMyDevice() = runTest {
        dao.insertToMyDevice(myDeviceContent = myDeviceContent)
        dao.deleteDevice(myDeviceContent = myDeviceContent)

        val getMyDevices = dao.getAllMyDevices(isFavorite = true).first()

        assertFalse(getMyDevices.contains(myDeviceContent))
    }

    @Test
    @Throws(Exception::class)
    fun updateDevice() = runTest {
        val deviceContents = listOf(myDeviceContent.convertToDeviceContent())

        dao.insertAllDevices(deviceContentList = deviceContents)

        // make it unFavorite ( looks like we unFavorite and delete it at the same time )
        val updateDeviceContent = DeviceContent(
            id = "10",
            os = "Android 9",
            status = "Available",
            price = 400,
            currency = "USD",
            isFavorite = false,
            imageUrl = "https://shop.samsung.com/ie/images/products/27514/14920/600x600/SM-G996BZSGEUA.png",
            title = "Samsung Galaxy A20",
            description = "Device Desc",
            company = "Samsung"
        )

        dao.updateDeviceContent(deviceContent = updateDeviceContent)
        val getMyDevices = dao.getAllDevices().first()

        assertTrue(getMyDevices.contains(updateDeviceContent))
    }

}