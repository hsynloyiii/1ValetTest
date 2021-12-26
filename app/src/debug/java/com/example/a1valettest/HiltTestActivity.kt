package com.example.a1valettest

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/*
As we can't use hilt with empty activity which doesn't annotated with @AndroidEntryPoint, so we create it by our own
hand
 */
@AndroidEntryPoint
class HiltTestActivity: AppCompatActivity()