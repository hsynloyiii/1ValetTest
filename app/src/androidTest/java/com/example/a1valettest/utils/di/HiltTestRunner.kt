package com.example.a1valettest.utils.di

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.a1valettest.utils.ValetApplication
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, CustomTest::class.java.name, context)
    }

}

@CustomTestApplication(ValetApplication::class)
interface CustomTest