package com.example.weatherapp.presentation

import androidx.annotation.DrawableRes
import com.example.weatherapp.R

enum class WeatherType(@DrawableRes val imageId: Int) {
    Clear(R.drawable.clear_sky),
    Clouds(R.drawable.clouds),
    Rain(R.drawable.rain),
    Snow(R.drawable.snow),
    Thunderstorm(R.drawable.thunderstorm),
    Fog(R.drawable.fog)
}