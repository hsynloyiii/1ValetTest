package com.example.a1valettest.view.fragment

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.a1valettest.R
import com.example.a1valettest.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class DeviceDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationToHomeFragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<DeviceDetailFragment> {
            Navigation.setViewNavController(requireView(), navController)

        }

        Espresso.onView(ViewMatchers.withId(R.id.materialContainerAllDevices))
            .perform(ViewActions.click())


    }

}