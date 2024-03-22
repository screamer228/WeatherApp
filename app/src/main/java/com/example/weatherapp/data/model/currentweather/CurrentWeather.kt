package com.example.weatherapp.data.model.currentweather

import com.example.weatherapp.data.model.forecast.Coord
import com.example.weatherapp.data.model.forecast.Clouds
import com.example.weatherapp.data.model.forecast.Weather
import com.example.weatherapp.data.model.forecast.Wind

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