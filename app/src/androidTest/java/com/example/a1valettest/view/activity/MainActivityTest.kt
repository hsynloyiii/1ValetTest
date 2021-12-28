package com.example.a1valettest.view.activity

import android.widget.ImageButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.a1valettest.R
import com.example.a1valettest.view.adapter.HomeAdapter
import com.example.a1valettest.withActionIconDrawable
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*
This test class is for host ( whole fragments ) which can we see the interaction between navigation that could show the detail or
our popBackPress is working fine and show the corresponding fragment as well with some other testing like DrawerLayout
The other more detail tests for specific fragment is on their test class file (like searching or some other more detail matching test)
 */
@LargeTest
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testDrawerLayoutWithAllItsItem() {
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentHome))
            )
        )
            .perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isOpen()))

        onView(withId(R.id.myDevicesFragment)).perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isClosed()))

        onView(withId(R.id.coordinatorContainerFragmentMyDevices)).check(matches(isDisplayed()))

        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentMyDevices))
            )
        )
            .perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isOpen()))

        onView(withId(R.id.settingFragment)).perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isClosed()))

        onView(withId(R.id.containerFragmentSetting)).check(matches(isDisplayed()))

        onView(
            withId(R.id.containerFragmentSetting)
        )
            .perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isOpen()))

        onView(withId(R.id.homeFragment)).perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isClosed()))

        onView(withId(R.id.coordinatorContainerFragmentHome)).check(matches(isDisplayed()))
    }

    @Test
    fun testIsListAllDevicesVisible() {
        onView(withId(R.id.recyclerViewFragmentHome)).check(matches(isDisplayed()))
    }

    @Test
    fun testSelectListItemIsDetailFragmentVisible() {
        onView(withId(R.id.recyclerViewFragmentHome))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<HomeAdapter.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.textViewDeviceNameFragmentDeviceDetail)).check(matches(withText("Samsung Galaxy S21")))
    }

    @Test
    fun testBackNavigationToAllDeviceListFragment() {
        onView(withId(R.id.recyclerViewFragmentHome))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<HomeAdapter.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.textViewDeviceNameFragmentDeviceDetail)).check(matches(withText("Samsung Galaxy S21")))

        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentDeviceDetail))
            )
        ).perform(click())

        onView(withId(R.id.recyclerViewFragmentHome)).check(matches(isDisplayed()))
    }


    @Test
    fun testTheWholeFlowOfAddingRemovingDevice() {
        // go to detail and check
        onView(withId(R.id.recyclerViewFragmentHome))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<HomeAdapter.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withId(R.id.textViewDeviceNameFragmentDeviceDetail)).check(matches(withText("Samsung Galaxy S21")))

        // add device to myDevice and check it successfully added
        onView(withActionIconDrawable(R.drawable.ic_round_star_outline_24)).check(
            matches(
                isDisplayed()
            )
        ).perform(click())

        onView(withText(R.string.successfullyAdded)).check(matches(isDisplayed()))

        onView(withActionIconDrawable(R.drawable.ic_round_star_24)).check(matches(isDisplayed()))

        // get back to homeFragment and check recyclerView is on the screen
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentDeviceDetail))
            )
        ).perform(click())

        onView(withId(R.id.recyclerViewFragmentHome)).check(matches(isDisplayed()))

        // open drawer and go to MyDevice fragment and check Item is added
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentHome))
            )
        )
            .perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isOpen()))

        onView(withId(R.id.myDevicesFragment)).perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isClosed()))

        onView(withText("Samsung Galaxy S21")).check(matches(isDisplayed()))

        // go to detail and remove the item with Dialog
        onView(withId(R.id.recyclerViewFragmentMyDevices))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<HomeAdapter.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withActionIconDrawable(R.drawable.ic_round_star_24)).check(matches(isDisplayed()))
            .perform(
                click()
            )

        onView(withText(R.string.remove)).check(matches(isDisplayed())).perform(click())
            .check(doesNotExist())

        onView(withText(R.string.successfullyRemoved)).check(matches(isDisplayed()))

        onView(withActionIconDrawable(R.drawable.ic_round_star_outline_24)).check(
            matches(
                isDisplayed()
            )
        )

        // Then go back to myDevice fragment and check there is no item there
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentDeviceDetail))
            )
        )
            .perform(click())

        onView(withText("Samsung Galaxy S21")).check(doesNotExist())

        onView(withId(R.id.textEmptyMyDeviceList)).check(matches(isDisplayed()))
    }
}