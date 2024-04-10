package com.example.weatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.currentweather.WeatherResult
import com.example.weatherapp.data.model.forecast.Coord
import com.example.weatherapp.data.model.forecast.FiveDayForecast
import com.example.weatherapp.data.repository.PrefsRepository
import com.example.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val prefsRepository: PrefsRepository
) : ViewModel() {

    private val coordinates: MutableLiveData<Coord> = MutableLiveData()
    val coordinatesResult: LiveData<Coord> = coordinates

    private val currentWeather: MutableLiveData<WeatherResult> = MutableLiveData()
    val currentWeatherResult: LiveData<WeatherResult> = currentWeather

    private val forecast: MutableLiveData<FiveDayForecast> = MutableLiveData()
    val forecastResult: LiveData<FiveDayForecast> = forecast

    fun getCityFromPrefs(): String {
        return prefsRepository.getCity()
    }

    fun saveDataInPrefs(key: String, value: String) {
        prefsRepository.saveDataInPrefs(key, value)
    }

    fun getCoordinates(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val coordinatesApiResult = weatherRepository.getLocationCoordinates(city)
            coordinates.postValue(coordinatesApiResult)
        }
    }

    suspend fun getCurrentWeather(lat: Double, lon: Double) {
        withContext(Dispatchers.IO) {
            val currentWeatherResult = weatherRepository.getCurrentWeather(lat, lon)
            currentWeather.postValue(currentWeatherResult)
        }
    }

    suspend fun getForecast(lat: Double, lon: Double) {
        withContext(Dispatchers.IO) {
            val forecastResult = weatherRepository.getForecast(lat, lon)
            forecast.postValue(forecastResult)
        }
    }
}