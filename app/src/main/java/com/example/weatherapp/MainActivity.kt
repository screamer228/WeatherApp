package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherApi
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.floor

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

            val result = retrofitClient.getGeocoding("Rostov_on_Don", 1, appId)

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
                showToast(currentWeather.body()?.main?.temp.toString() ?: "")

//                locationLabel.text = "Location: ${result.body()?.first()?.name ?: ""}"
//                currentWeatherLabel.text = currentWeather.body()?.weather?.first()?.main ?: ""
//                forecastLabel.text = forecast.body()?.list?.first()?.weather?.first()?.description ?: ""
            }
        }
        prepareViewPager()
    }

    private fun showToast(message: String) {
        val messageResult = floor(message.toDouble() * 10) / 10
        val duration = Toast.LENGTH_SHORT // или Toast.LENGTH_LONG для длительного отображения
        val toast = Toast.makeText(applicationContext, messageResult.toString(), duration)
        toast.show()
    }

    private fun prepareViewPager() {
        val fragmentList = arrayListOf(
            WeatherFragment.newInstance(),
            ForecastFragment.newInstance()
        )
        val tabTitleArray = arrayOf("Weather", "Forecast")

        viewPager.adapter = ViewPagerAdapter(this, fragmentList)

        TabLayoutMediator(tabLayout, viewPager) {
            tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }
}