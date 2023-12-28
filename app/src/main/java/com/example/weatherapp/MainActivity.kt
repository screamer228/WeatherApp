package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherApi
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val retrofitClient = RetrofitHelper.getInstance().create(WeatherApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            val result = retrofitClient.getGeocoding("London", limit = 1, "fb39671b2fc42219a9e9e57212ba5b26")
        }
    }
}