package com.example.a1valettest.view.fragment

import androidx.navigation.NavController
import androidx.navigation.Navigation.setViewNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.a1valettest.R
import com.example.a1valettest.utils.di.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
//@Config(application = HiltTestApplication::class)
class HomeFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun textNavigationToDeviceDetailFragment() {
//        val navController = TestNavHostController(
//            ApplicationProvider.getApplicationContext()
//        )

        val navController = mock(NavController::class.java)
//        `when`(navController.popBackStack()).thenReturn()
        launchFragmentInHiltContainer<HomeFragment> {
//            navController.setGraph(R.navigation.main_nav)

            setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.materialContainerAllDevices)).perform(click())
//        assertThat(navController.currentDestination?.id).isEqualTo(R.id.deviceDetailFragment)

        // now verify the navigate function of navController was actually called with right parameter
        verify(navController).navigate(HomeFragmentDirections.actionHomeFragmentToDeviceDetailFragment())
    }

}