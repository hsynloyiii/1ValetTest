package com.example.a1valettest.view.activity

import android.content.Context
import android.graphics.Color
import android.widget.ImageButton
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
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
import com.example.a1valettest.utils.database.DeviceDataBase
import com.example.a1valettest.utils.rule.EspressoIdlingResourceRule
import com.example.a1valettest.utils.withBgColor
import com.example.a1valettest.view.adapter.HomeAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.AllOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

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


    @get:Rule(order = 2)
    val idlingResourceRule = EspressoIdlingResourceRule()

    @Inject
    lateinit var db: DeviceDataBase

    @Before
    fun setUp() {
        hiltRule.inject()
        db.clearAllTables()
    }

    @After
    fun tearDown() {
        db.clearAllTables()
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
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentSetting))
            )
        )
            .perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isOpen()))

        onView(withId(R.id.homeFragment)).perform(click())

        onView(withId(R.id.coordinatorContainerFragmentHome)).check(matches(isDisplayed()))

        onView(withId(R.id.mainDrawerLayout)).check(matches(isClosed()))
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
        onView(withId(R.id.favoriteDevice)).check(
            matches(
                isDisplayed()
            )
        ).perform(click())

        onView(
            AllOf.allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                withText(R.string.successfullyAdded)
            )
        ).check(matches(isDisplayed()))

        onView(withId(R.id.favoriteDevice)).check(matches(isDisplayed()))

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

        onView(withId(R.id.favoriteDevice)).check(matches(isDisplayed()))
            .perform(
                click()
            )

        onView(withText(R.string.remove)).check(matches(isDisplayed())).perform(click())
            .check(doesNotExist())

        onView(
            allOf(
                withId(com.google.android.material.R.id.snackbar_text),
                withText(R.string.successfullyRemoved)
            )
        ).check(matches(isDisplayed()))

        onView(withId(R.id.favoriteDevice)).check(
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

    @Test
    fun testNightAndLightMode() {

        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentHome))
            )
        )
            .perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isOpen()))

        onView(withId(R.id.settingFragment)).perform(click())

        onView(withId(R.id.mainDrawerLayout)).check(matches(isClosed()))

        onView(withId(R.id.containerFragmentSetting)).check(matches(isDisplayed()))

        onView(withId(R.id.linearThemeModeFragmentSetting)).perform(click())

        onView(withText("Light")).check(matches(isDisplayed())).perform(click())

//        onView(withChild(withId(R.id.containerFragmentSetting))).check(
//            matches(
//                withBgColor(
//                    Color.parseColor(
//                        "#FDFCF5"
//                    )
//                )
//            )
//        )
    }
}