package com.example.a1valettest.view.fragment

import android.widget.ImageButton
import android.widget.TextView
import androidx.test.filters.MediumTest
import com.example.a1valettest.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.a1valettest.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class SettingFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        launchFragmentInHiltContainer<SettingFragment>()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testUIIsOnTheScreen() {
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbarFragmentSetting))
            )
        ).check(matches(isDisplayed()))

        onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(R.id.toolbarFragmentSetting))
            )
        ).check(matches(withText(R.string.setting)))

        onView(
            allOf(
                withId(R.id.textViewChooseThemeFragmentSetting),
                withText(R.string.chooseThemeMode)
            )
        ).check(matches(isDisplayed()))

        onView(
            withId(R.id.imageViewNightLightModeFragmentSetting)
        ).check(matches(isDisplayed()))
    }

    @Test
    fun testThemeSingleChoiceDialog() {

        onView(withId(R.id.linearThemeModeFragmentSetting)).check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.chooseTheme)).check(matches(isDisplayed()))

        onView(withText("Dark")).check(matches(isDisplayed()))
        onView(withText("Light")).check(matches(isDisplayed()))
        onView(withText("System default")).check(matches(isDisplayed())).perform(click())

        onView(withText(R.string.chooseTheme)).check(doesNotExist())

    }
}