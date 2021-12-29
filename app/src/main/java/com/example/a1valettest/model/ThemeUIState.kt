package com.example.a1valettest.model

// position = 0 = light, position = 1 = night , position = 2 = follow system night/light
data class ThemeUIState(
    var nightModeByPosition: Int,
    var selectedThemeItemPosition: Int
)