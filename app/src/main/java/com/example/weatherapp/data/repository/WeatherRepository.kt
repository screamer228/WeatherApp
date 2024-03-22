package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.currentweather.WeatherResult
import com.example.weatherapp.data.model.forecast.Coord
import com.example.weatherapp.data.model.forecast.FiveDayForecast

interface WeatherRepository {

    suspend fun getLocationCoordinates(city: String): Coord

    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherResult

    suspend fun getForecast(lat: Double, lon: Double): FiveDayForecast
}