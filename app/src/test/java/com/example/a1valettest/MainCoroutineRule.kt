package com.example.a1valettest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val dispatchers: TestDispatcher = StandardTestDispatcher()
): TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher = dispatchers)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        dispatchers.cancel()
    }

}