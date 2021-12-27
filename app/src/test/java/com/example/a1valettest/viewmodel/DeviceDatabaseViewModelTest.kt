package com.example.a1valettest.viewmodel

import com.example.a1valettest.MainCoroutineRule
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.model.MyDeviceContent
import com.example.a1valettest.repository.FakeDeviceDatabaseRepository
import com.example.a1valettest.utils.convertToDeviceContent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DeviceDatabaseViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)

    private lateinit var viewModel: DeviceDatabaseViewModel

    private lateinit var myDeviceContent: MyDeviceContent

    // just use myDeviceContent data class but convert it to DeviceContent data class ( avoid boilerplate code )
    private lateinit var deviceContentLists: List<DeviceContent>

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

        deviceContentLists = listOf(myDeviceContent.convertToDeviceContent())
    }

    // My Device testing
    @Test
    fun `insert device to my device then delete and check empty list, return true`() =
        scope.runTest {
            viewModel.insertToMyDevice(myDeviceContent = myDeviceContent)

            viewModel.deleteDevice(myDeviceContent = myDeviceContent)

            val getMyDevices = viewModel.getMyDevices.first()

            assertEquals(emptyList<MyDeviceContent>(), getMyDevices)
        }


    @Test
    fun `insert device to my device then check the availability, return true`() =
        scope.runTest {
            viewModel.insertToMyDevice(myDeviceContent = myDeviceContent)

            val getMyDevices = viewModel.getMyDevices.first()

            assertTrue(
                "True if myDeviceContent is in the myDeviceList",
                getMyDevices.contains(myDeviceContent)
            )
        }

    // All Device testing
    @Test
    fun `insert device to all devices then check the availability, return true`() =
        scope.runTest {
            viewModel.insertToAllDevices(deviceContentList = deviceContentLists)

            assertEquals(deviceContentLists, viewModel.getAllDevices.first())
        }

    @Test
    fun `insert device to all devices then update it, finally check the equality with old list (should not be equal), return true`() =
        scope.runTest {
            viewModel.insertToAllDevices(deviceContentList = deviceContentLists)

            // just make it favorite
            val updateDeviceContent = DeviceContent(
                id = "2",
                os = "Android 11",
                status = "Available",
                price = 700,
                currency = "USD",
                isFavorite = true,
                imageUrl = "https://oneclickroot.com/wp-content/uploads/2020/07/samsung-galaxy-a50.png",
                title = "Samsung Galaxy A50",
                description = "Capture more fabulousness with the revolutionary 123° ultra-wide lens. Capture more in every moment with 240FPS slow-mo video. Redefine your visual experience with the Super AMOLED 16.21cm (6.4\") FHD+ Infinity-U display. Unlock coolness with the on-screen fingerprint sensor. Power up your phone in no time with 15W Fast Charging. Play games, stream videos, and vlog - all at the same time!.",
                company = "Samsung"
            )

            viewModel.updateDeviceContent(deviceContent = updateDeviceContent)

            assertNotEquals(deviceContentLists, viewModel.getAllDevices.first())
        }

    @Test
    fun `insert device to all devices then update it, finally check the equality with updated list, return true`() =
        scope.runTest {
            viewModel.insertToAllDevices(deviceContentList = deviceContentLists)

            val updateDeviceContent = DeviceContent(
                id = "2",
                os = "Android 11",
                status = "Available",
                price = 700,
                currency = "USD",
                isFavorite = true,
                imageUrl = "https://oneclickroot.com/wp-content/uploads/2020/07/samsung-galaxy-a50.png",
                title = "Samsung Galaxy A50",
                description = "Capture more fabulousness with the revolutionary 123° ultra-wide lens. Capture more in every moment with 240FPS slow-mo video. Redefine your visual experience with the Super AMOLED 16.21cm (6.4\") FHD+ Infinity-U display. Unlock coolness with the on-screen fingerprint sensor. Power up your phone in no time with 15W Fast Charging. Play games, stream videos, and vlog - all at the same time!.",
                company = "Samsung"
            )

            viewModel.updateDeviceContent(deviceContent = updateDeviceContent)

            assertTrue(
                "True if updated data class is in the deviceContentList",
                viewModel.getAllDevices.first().contains(updateDeviceContent)
            )
        }
}