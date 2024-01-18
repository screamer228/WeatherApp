package com.example.weatherapp.repository

interface PrefsRepository {

    fun getCity() : String
    fun saveDataInPrefs(key: String, value: String)

}