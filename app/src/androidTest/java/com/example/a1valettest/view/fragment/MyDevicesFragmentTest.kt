package com.example.a1valettest.view.fragment

import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.navOptions
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.a1valettest.R
import com.example.a1valettest.utils.launchFragmentInHiltContainer
import com.example.a1valettest.model.DeviceContent
import com.example.a1valettest.utils.convertToMyDeviceContent
import com.example.a1valettest.utils.database.DeviceDao
import com.example.a1valettest.utils.database.DeviceDataBase
import com.example.a1valettest.utils.rule.EspressoIdlingResourceRule
import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.view.fragment.factory.MainFragmentFactory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MyDevicesFragmentTest {

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    @Inject
    lateinit var dao: DeviceDao

    @Inject
    lateinit var db: DeviceDataBase

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val idlingResourceRule = EspressoIdlingResourceRule()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        db.clearAllTables()
    }

    @Test
    fun testMyDeviceToolbarTitle() {
        launchFragmentInHiltContainer<MyDevicesFragment>(fragmentFactory = fragmentFactory)
        onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(R.id.toolbarFragmentMyDevices))
            )
        )
            .check(matches(withText(R.string.my_devices)))
    }

    @Test
    fun testIsListAllDeviceVisible() {
        launchFragmentInHiltContainer<MyDevicesFragment>(fragmentFactory = fragmentFactory)
        // Recycler must be in the UI, there is no need that have specific data or not
        onView(withId(R.id.recyclerViewFragmentMyDevices))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testNavigationToDeviceDetailFragmentInItemRecyclerView() = runTest {

        val deviceContent = DeviceContent(
            id = "1",
            os = "Android 12",
            status = "Sold",
            price = 800,
            currency = "USD",
            isFavorite = true,
            imageUrl = "https://shop.samsung.com/ie/images/products/27514/14920/600x600/SM-G996BZSGEUA.png",
            title = "Samsung Galaxy S21",
            description = "The Samsung Galaxy S21+ 5G comes with a 6.7 inch touchscreen with 394PPI. It packs a 64-megapixel rear camera and a 10-megapixel selfie-camera. This is all powered by the Exynos 2100 International chipset and 8GB of RAM.",
            company = "Samsung"
        )

        // as here wanna navigate to detail, created fake data and insert it to my devices
        dao.insertToMyDevice(myDeviceContent = deviceContent.convertToMyDeviceContent())

        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<MyDevicesFragment>(
            fragmentFactory = fragmentFactory

        ) {
            Navigation.setViewNavController(requireView(), navController)
        }


        onView(withId(R.id.recyclerViewFragmentMyDevices))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<HomeAdapter.ViewHolder>(
                    0,
                    click()
                )
            )


        // now verify the navigate function of navController was actually called with right parameter
        verify(navController).navigate(
            MyDevicesFragmentDirections.actionMyDevicesFragmentToDeviceDetailFragment(deviceContent = deviceContent),
            FragmentNavigatorExtras()
        )
    }


    @Test
    fun testSearchViewNoResult() = runTest {
        val deviceContent = DeviceContent(
            id = "1",
            os = "Android 12",
            status = "Sold",
            price = 800,
            currency = "USD",
            isFavorite = true,
            imageUrl = "https://shop.samsung.com/ie/images/products/27514/14920/600x600/SM-G996BZSGEUA.png",
            title = "Samsung Galaxy S21",
            description = "The Samsung Galaxy S21+ 5G comes with a 6.7 inch touchscreen with 394PPI. It packs a 64-megapixel rear camera and a 10-megapixel selfie-camera. This is all powered by the Exynos 2100 International chipset and 8GB of RAM.",
            company = "Samsung"
        )

        // as here wanna navigate to detail, created fake data and insert it to my devices
        dao.insertToMyDevice(myDeviceContent = deviceContent.convertToMyDeviceContent())

        launchFragmentInHiltContainer<MyDevicesFragment>(fragmentFactory = fragmentFactory)


        onView(
            withId(R.id.search)
        ).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("This item is not in my device list"))
        onView(withId(R.id.textNoResult))
            .check(matches(withText(R.string.noResult)))

        Espresso.closeSoftKeyboard()

        // at the end press back and close searchView
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentMyDevices))
            )
        ).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .check(doesNotExist())
    }

    @Test
    fun testSearchViewWithResult() = runTest {
        val deviceContent = DeviceContent(
            id = "1",
            os = "Android 12",
            status = "Sold",
            price = 800,
            currency = "USD",
            isFavorite = true,
            imageUrl = "https://shop.samsung.com/ie/images/products/27514/14920/600x600/SM-G996BZSGEUA.png",
            title = "Samsung Galaxy S21",
            description = "The Samsung Galaxy S21+ 5G comes with a 6.7 inch touchscreen with 394PPI. It packs a 64-megapixel rear camera and a 10-megapixel selfie-camera. This is all powered by the Exynos 2100 International chipset and 8GB of RAM.",
            company = "Samsung"
        )

        // as here wanna navigate to detail, created fake data and insert it to my devices
        dao.insertToMyDevice(myDeviceContent = deviceContent.convertToMyDeviceContent())

        launchFragmentInHiltContainer<MyDevicesFragment>(fragmentFactory = fragmentFactory)

        onView(
            withId(R.id.search)
        ).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("Samsung Galaxy S21"))

        onView(withId(R.id.linearNoResult))
            .check(doesNotExist())

        Espresso.closeSoftKeyboard()

        // at the end press back and close searchView
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentMyDevices))
            )
        ).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .check(doesNotExist())
    }

    @Test
    fun testSearchWithNoListExist() {
        launchFragmentInHiltContainer<MyDevicesFragment>(fragmentFactory = fragmentFactory)

        onView(withId(R.id.search)).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("Samsung Galaxy S21"))

        onView(withId(R.id.textEmptyMyDeviceList)).check(matches(isDisplayed()))

        // at the end press back and close searchView
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentMyDevices))
            )
        ).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .check(doesNotExist())
    }

}