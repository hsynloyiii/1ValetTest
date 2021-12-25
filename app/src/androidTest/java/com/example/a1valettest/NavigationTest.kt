package com.example.a1valettest

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.a1valettest.view.fragment.HomeFragment
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.robolectric.annotation.Config

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltAndroidRule.inject()
    }

    @Test
    fun testNavigationToDeviceDetailScreen() {
        val navController = TestNavHostController(
            context = ApplicationProvider.getApplicationContext()
        )

        val homeFragment = launchFragmentInContainer<HomeFragment>()


        homeFragment.onFragment { fragment ->
            navController.setGraph(R.navigation.main_nav)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.materialItemDevices)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.deviceDetailFragment)
//        val scenario = launchFragmentInContainer {
//            HomeFragment().also { fragment ->
//                fragment.view
//            }
//        }
    }

}