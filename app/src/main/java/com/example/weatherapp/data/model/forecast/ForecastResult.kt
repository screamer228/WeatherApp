package com.example.weatherapp.data.model.forecast

data class ForecastResult(
    val date: String,
    val temp: Double,
    val description: String,
    val main: String
)
