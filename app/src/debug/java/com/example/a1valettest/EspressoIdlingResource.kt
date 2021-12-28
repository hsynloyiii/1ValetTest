package com.example.a1valettest

import androidx.test.espresso.idling.CountingIdlingResource

// As we are getting data from DB it might cause some times or delay to load, so we add EspressoIdlingResource
object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"


    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow)
            countingIdlingResource.decrement()
    }
}