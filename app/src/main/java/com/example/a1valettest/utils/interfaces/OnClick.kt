package com.example.a1valettest.utils.interfaces

import android.view.View
import com.example.a1valettest.model.DeviceContent

interface OnClick {

    interface HomeAdapter {
        fun navigateToDetail(view: View, deviceContent: DeviceContent)
    }

}