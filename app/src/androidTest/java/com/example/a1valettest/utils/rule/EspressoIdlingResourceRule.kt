package com.example.a1valettest.utils.rule

import androidx.test.espresso.IdlingRegistry
import com.example.a1valettest.EspressoIdlingResource
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class EspressoIdlingResourceRule: TestWatcher() {

    private val idlingResource = EspressoIdlingResource.countingIdlingResource

    // Before
    override fun starting(description: Description?) {
        IdlingRegistry.getInstance().register(idlingResource)
        super.starting(description)
    }

    // After
    override fun finished(description: Description?) {
        IdlingRegistry.getInstance().unregister(idlingResource)
        super.finished(description)
    }

}