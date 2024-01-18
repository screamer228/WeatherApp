package com.example.weatherapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.viewmodel.MainViewModel
import com.google.android.material.search.SearchBar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.floor

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager2
    private lateinit var inputField: TextInputLayout

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        inputField = binding.inputField


        inputField.setEndIconOnClickListener {
            performSearch()
        }

        inputField.editText?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                performSearch()
                return@setOnKeyListener true
            }
            false
        }

        mainViewModel.coordinatesResult.observe(this, Observer {
            lifecycleScope.launch(Dispatchers.Main) {
                mainViewModel.getCurrentWeather(it.lat, it.lon)
                mainViewModel.getForecast(it.lat, it.lon)
            }
        })
        prepareViewPager()
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

    private fun performSearch() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isActive) {
            inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.getCoordinates(inputField.editText?.text.toString())
        }
    }
}