package com.example.weatherapp.model.currentweather

import com.example.weatherapp.model.forecast.Coord
import com.example.weatherapp.model.forecast.Clouds
import com.example.weatherapp.model.forecast.Weather
import com.example.weatherapp.model.forecast.Wind

data class CurrentWeather(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)