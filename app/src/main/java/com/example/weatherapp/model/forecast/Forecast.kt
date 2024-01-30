package com.example.weatherapp.model.forecast

data class Forecast(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: MainForecast,
    val pop: Double,
    val rain: Rain,
    val sys: SysForecast,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)