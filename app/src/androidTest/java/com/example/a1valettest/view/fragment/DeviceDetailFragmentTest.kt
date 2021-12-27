package com.example.a1valettest.view.fragment

import android.widget.ImageButton
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
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
import org.mockito.Mockito.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.a1valettest.withActionIconDrawable
import org.hamcrest.core.IsNot.not


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
    fun testUIIsOnTheScreen() {
        val bundle = DeviceDetailFragmentArgs(deviceContent = deviceContent).toBundle()
        launchFragmentInHiltContainer<DeviceDetailFragment>(
            fragmentArgs = bundle
        )

        onView(withId(R.id.imageViewDevice)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewCompanyFragmentDeviceDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDeviceNameFragmentDeviceDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDeviceDescriptionFragmentDeviceDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDevicePriceFragmentDeviceDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDeviceStatusFragmentDeviceDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDeviceOSFragmentDeviceDetail)).check(matches(isDisplayed()))
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
                withParent(withId(R.id.toolbarFragmentDeviceDetail))
            )
        ).perform(click())

        verify(navController).popBackStack()
    }

    @Test
    fun testMakeFavoriteAndRemoveWithDialogWholeFlow() {

        val bundle = DeviceDetailFragmentArgs(deviceContent = deviceContent).toBundle()
        launchFragmentInHiltContainer<DeviceDetailFragment>(
            fragmentArgs = bundle
        )

        onView(withActionIconDrawable(R.drawable.ic_round_star_outline_24)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withActionIconDrawable(R.drawable.ic_round_star_outline_24)).perform(click())
        onView(withActionIconDrawable(R.drawable.ic_round_star_24)).check(matches(isDisplayed()))
        onView(withActionIconDrawable(R.drawable.ic_round_star_24)).perform(click())
        onView(withText(R.string.remove)).check(matches(isDisplayed()))
        onView(withText(R.string.remove)).perform(click())
        onView(withText(R.string.remove)).check(doesNotExist())
        onView(withActionIconDrawable(R.drawable.ic_round_star_outline_24)).check(
            matches(
                isDisplayed()
            )
        )

    }

}

