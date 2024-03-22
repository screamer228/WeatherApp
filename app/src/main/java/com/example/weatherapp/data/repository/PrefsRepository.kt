package com.example.weatherapp.data.repository

interface PrefsRepository {

    fun getCity(): String
    fun saveDataInPrefs(key: String, value: String)

}