package com.example.wall_et_mobile.data.preferences

import android.content.Context
import android.content.SharedPreferences
import java.util.Locale

class LanguagePreference(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)
    private val languageKey = "language_setting"

    fun getLanguage(): String {
        return prefs.getString(languageKey, "en") ?: "en"
    }

    fun setLanguage(languageCode: String) {
        prefs.edit().putString(languageKey, languageCode).apply()
    }
}