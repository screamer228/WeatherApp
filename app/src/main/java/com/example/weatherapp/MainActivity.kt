package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherApi
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager2

    private lateinit var binding : ActivityMainBinding

    private val appId = "e6bc672f315ea264a8e0e568a6376e50"

    private val retrofitClient = RetrofitHelper.getInstance().create(WeatherApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        lifecycleScope.launch(Dispatchers.IO) {

            val result = retrofitClient.getGeocoding("London", 1, appId)

            val latResult = result.body()?.first()?.lat ?: 0.0
            val lonResult = result.body()?.first()?.lon ?: 0.0

            val currentWeather = retrofitClient.getCurrentWeather(latResult, lonResult, appId, "metric")

            val forecast = retrofitClient.getForecast(latResult, lonResult, appId, "metric")

            Log.d("testingRetrofit", "geocoding ---> ${result.errorBody()}")
            Log.d("testingRetrofit", "geocoding ---> ${result.message()}")
            Log.d("testingRetrofit", "geocoding ---> ${result.body()}")
            Log.d("testingRetrofit", "currentWeather ---> ${currentWeather.body()}")
            Log.d("testingRetrofit", "currentWeather ---> ${currentWeather.isSuccessful}")
            Log.d("testingRetrofit", "forecast ---> ${forecast.message()}")
            Log.d("testingRetrofit", "forecast ---> ${forecast.body()}")

            withContext(Dispatchers.Main) {
//                locationLabel.text = "Location: ${result.body()?.first()?.name ?: ""}"
//                currentWeatherLabel.text = currentWeather.body()?.weather?.first()?.main ?: ""
//                forecastLabel.text = forecast.body()?.list?.first()?.weather?.first()?.description ?: ""
            }
        }
    }
}