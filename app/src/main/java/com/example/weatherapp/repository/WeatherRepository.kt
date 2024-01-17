package com.example.weatherapp.repository

import com.example.weatherapp.model.currentweather.WeatherResult
import com.example.weatherapp.model.forecast.Coord
import com.example.weatherapp.model.forecast.FiveDayForecast


interface WeatherRepository {

    suspend fun getLocationCoordinates(city: String) : Coord

    suspend fun getCurrentWeather(lat: Double, lon: Double) : WeatherResult

    suspend fun getForecast(lat: Double, lon: Double) : FiveDayForecast
}