package com.example.weatherapp.data.model.currentweather

data class WeatherResult(
    val main: String,
    val description: String,
    val temp: Double,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int
)
