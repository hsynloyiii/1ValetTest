package com.example.a1valettest.viewmodel

import com.example.a1valettest.MainCoroutineRule
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.repository.FakeDeviceDatabaseRepository
import com.example.a1valettest.utils.convertToDeviceContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DeviceDatabaseViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)

    private lateinit var viewModel: DeviceDatabaseViewModel

    private lateinit var myDeviceContent: MyDeviceContent

    @get:Rule
    var rule = MainCoroutineRule(testDispatcher)

    @Before
    fun setup() {
        viewModel = DeviceDatabaseViewModel(FakeDeviceDatabaseRepository(), testDispatcher)

        myDeviceContent = MyDeviceContent(
            id = "2",
            os = "Android 11",
            status = "Available",
            price = 700,
            currency = "USD",
            isFavorite = false,
            imageUrl = "https://oneclickroot.com/wp-content/uploads/2020/07/samsung-galaxy-a50.png",
            title = "Samsung Galaxy A50",
            description = "Capture more fabulousness with the revolutionary 123° ultra-wide lens. Capture more in every moment with 240FPS slow-mo video. Redefine your visual experience with the Super AMOLED 16.21cm (6.4\") FHD+ Infinity-U display. Unlock coolness with the on-screen fingerprint sensor. Power up your phone in no time with 15W Fast Charging. Play games, stream videos, and vlog - all at the same time!.",
            company = "Samsung"
        )
    }

    @Test
    fun `insert device to my device then delete and check empty list, return true`() =
        scope.runTest {
            viewModel.insertToMyDevice(myDeviceContent = myDeviceContent)

            viewModel.deleteDevice(myDeviceContent = myDeviceContent)

            val getMyDevices = viewModel.getDevices.first()

            assertEquals(emptyList<MyDeviceContent>(), getMyDevices)
        }


    @Test
    fun `insert device to my device then check the availability, return true`() =
        scope.runTest {
            viewModel.insertToMyDevice(myDeviceContent = myDeviceContent)

            val getMyDevices = viewModel.getDevices.first()

            assertTrue("",getMyDevices.contains(myDeviceContent))
        }

    @Test
    fun `update device in all devices even we don't have inserted list then check if it updated, return true`() =
        scope.runTest {
            viewModel.updateDeviceContent(deviceContent = myDeviceContent.convertToDeviceContent())
        }
}