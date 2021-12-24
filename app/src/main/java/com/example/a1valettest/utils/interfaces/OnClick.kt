package com.example.a1valettest.utils.interfaces

import android.view.View
import com.example.a1valettest.model.DeviceContent

interface OnClick {

    // As we might add specific fun into specific Class , divide them into different interfaces
    interface HomeAdapter {
        fun navigateToDetail(
            view: View,
            deviceContent: DeviceContent
        )
    }

    interface MyDeviceAdapter {
        fun navigateToDetail(
            view: View,
            deviceContent: DeviceContent
        )
    }

}