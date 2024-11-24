package com.example.wall_et_mobile.data.preferences

import android.content.Context
import android.content.SharedPreferences

class ThemePreference(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
    private val themeKey = "theme_setting"

    fun getThemeMode(): ThemeMode {
        val themeName = prefs.getString(themeKey, ThemeMode.SYSTEM.name) ?: ThemeMode.SYSTEM.name
        return ThemeMode.valueOf(themeName)
    }

    fun setThemeMode(theme: ThemeMode) {
        prefs.edit().putString(themeKey, theme.name).apply()
    }
}