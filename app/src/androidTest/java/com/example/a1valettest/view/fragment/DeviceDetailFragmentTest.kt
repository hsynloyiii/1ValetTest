package com.example.a1valettest.view.fragment

import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.filters.MediumTest
import com.example.a1valettest.R
import com.example.a1valettest.launchFragmentInHiltContainer
import com.example.a1valettest.model.DeviceContent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class DeviceDetailFragmentTest {

    private lateinit var deviceContent: DeviceContent

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
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

    }

    @Test
    fun testNavigationPopBackStack() {


        val navController = mock(NavController::class.java)

        val bundle = DeviceDetailFragmentArgs(deviceContent = deviceContent).toBundle()
        launchFragmentInHiltContainer<DeviceDetailFragment>(
            fragmentArgs = bundle
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }


        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(ViewMatchers.withId(R.id.toolbarFragmentDeviceDetail))
            )
        ).perform(click())

        verify(navController).popBackStack()
    }

}