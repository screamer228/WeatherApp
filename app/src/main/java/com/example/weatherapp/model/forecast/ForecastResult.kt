package com.example.weatherapp.model.forecast

data class ForecastResult(
    val date: String,
    val temp: Double,
    val description: String,
    val main: String
)
