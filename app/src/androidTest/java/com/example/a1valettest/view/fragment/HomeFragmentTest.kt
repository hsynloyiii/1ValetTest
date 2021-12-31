package com.example.a1valettest.view.fragment

import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.*
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
import com.example.a1valettest.utils.rule.EspressoIdlingResourceRule
import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.view.fragment.factory.MainFragmentFactory
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.*
import org.junit.runners.MethodSorters
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class HomeFragmentTest {

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val idlingResourceRule = EspressoIdlingResourceRule()

    @Before
    fun setUp() {
        hiltRule.inject()
        launchFragmentInHiltContainer<HomeFragment>(fragmentFactory = fragmentFactory)
    }

    @Test
    fun testHomeToolbarTitle() {
        onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(R.id.toolbarFragmentHome))
            )
        )
            .check(matches(withText(R.string.devices)))
    }

    @Test
    fun a_testIsListAllDeviceVisible() {
        onView(withId(R.id.recyclerViewFragmentHome))
            .check(matches(isDisplayed()))
    }

//    @Test
//    fun z_testNavigationToDeviceDetailFragmentInItemRecyclerView() {
//
//        val deviceContent = DeviceContent(
//            id = "1",
//            os = "Android 12",
//            status = "Sold",
//            price = 800,
//            currency = "USD",
//            isFavorite = false,
//            imageUrl = "https://shop.samsung.com/ie/images/products/27514/14920/600x600/SM-G996BZSGEUA.png",
//            title = "Samsung Galaxy S21",
//            description = "The Samsung Galaxy S21+ 5G comes with a 6.7 inch touchscreen with 394PPI. It packs a 64-megapixel rear camera and a 10-megapixel selfie-camera. This is all powered by the Exynos 2100 International chipset and 8GB of RAM.",
//            company = "Samsung"
//        )
//
//
//        val navController = mock(NavController::class.java)
//        launchFragmentInHiltContainer<HomeFragment>(
//            fragmentFactory = fragmentFactory
//        ) {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        onView(withText(deviceContent.title)).check(matches(isDisplayed()))
//
//        onView(withId(R.id.recyclerViewFragmentHome))
//            .perform(
//                RecyclerViewActions.actionOnItemAtPosition<HomeAdapter.ViewHolder>(
//                    0,
//                    click()
//                )
//            )
//
//        val materialCardView = MaterialCardView(ApplicationProvider.getApplicationContext())
//        // now verify the navigate function of navController was actually called with right parameter
//        verify(navController).navigate(
//            HomeFragmentDirections.actionHomeFragmentToDeviceDetailFragment(deviceContent = deviceContent),
//            FragmentNavigatorExtras(materialCardView to deviceContent.title)
//        )
//    }


    @Test
    fun testSearchViewNoResult() {
        onView(
            withId(R.id.search)
        ).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("This item is not in the list"))
        onView(withId(R.id.textNoResult)).check(matches(withText(R.string.noResult)))

        closeSoftKeyboard()

        // at the end press back and close searchView
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentHome))
            )
        ).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .check(doesNotExist())
    }

    @Test
    fun testSearchViewWithResult() {
        onView(
            withId(R.id.search)
        ).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("Xiaomi Mi 11 Ultra"))

        onView(withId(R.id.linearNoResult)).check(doesNotExist())

        closeSoftKeyboard()

        // at the end press back and close searchView
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentHome))
            )
        ).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .check(doesNotExist())
    }

}
