package com.example.a1valettest.view.fragment

import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import com.example.a1valettest.R
import com.example.a1valettest.launchFragmentInHiltContainer
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.view.fragment.factory.MainFragmentFactory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertTrue
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@LargeTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

//    private lateinit var navController: NavController

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testHomeToolbarTitle() {
        launchFragmentInHiltContainer<HomeFragment>(fragmentFactory = fragmentFactory)
        onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(R.id.toolbarFragmentHome))
            )
        )
            .check(matches(withText(R.string.devices)))
    }

    @Test
    fun testOpenDrawer() {
        launchFragmentInHiltContainer<HomeFragment>(fragmentFactory = fragmentFactory)
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentHome))
            )
        )
            .perform(click())

    }

    @Test
    fun testNavigationToDeviceDetailFragment() {

        val deviceContent = DeviceContent(
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


        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<HomeFragment>(
            fragmentFactory = fragmentFactory

        ) {
            Navigation.setViewNavController(requireView(), navController)
        }


        onView(withId(R.id.recyclerViewFragmentHome))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<HomeAdapter.ViewHolder>(
                    0,
                    click()
                )
            )


        // now verify the navigate function of navController was actually called with right parameter
        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToDeviceDetailFragment(deviceContent = deviceContent),
            navOptions {
                anim {
                    enter = R.anim.slide_in_right
                    exit = R.anim.scale_out
                    popEnter = R.anim.scale_in
                    popExit = R.anim.slide_out_right
                }
            }
        )
    }

}