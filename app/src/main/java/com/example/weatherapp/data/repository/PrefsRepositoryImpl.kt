package com.example.weatherapp.data.repository

import android.content.SharedPreferences
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : PrefsRepository {

    override fun getCity(): String {
        return sharedPreferences.getString(PREFS_CITY_KEY, PREFS_DEFAULT_VALUE)
            ?: PREFS_DEFAULT_VALUE
    }

    override fun saveDataInPrefs(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    companion object {
        const val PREFS_CITY_KEY = "cityKey"
        const val PREFS_NAME = "preferences"
        const val PREFS_DEFAULT_VALUE = ""
    }
}