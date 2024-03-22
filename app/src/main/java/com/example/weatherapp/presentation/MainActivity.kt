package com.example.weatherapp.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.weatherapp.R
import com.example.weatherapp.presentation.adapters.ViewPagerAdapter
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.data.repository.PrefsRepositoryImpl.Companion.PREFS_CITY_KEY
import com.example.weatherapp.presentation.viewmodel.MainViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var inputField: TextInputLayout
    private lateinit var plug: LinearLayout
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding()

        //loading from SharedPrefs
        inputField.editText?.setText(mainViewModel.getCityFromPrefs())
        inputField.editText?.clearFocus()

        screenDataValidation()

        search()

        clickListeners()

        observers()

        prepareViewPager()
    }

    private fun viewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager
        inputField = binding.inputField
        plug = binding.linearLayoutPlug
        progressIndicator = binding.progressIndicatorMain
    }

    private fun clickListeners() {
        inputField.setEndIconOnClickListener {
            hideKeyboardAndClearFocus(inputField.editText)
            search()
        }
        inputField.editText?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                hideKeyboardAndClearFocus(inputField.editText)
                search()
                return@setOnKeyListener true
            }
            false
        }
    }

    private fun observers() {
        mainViewModel.coordinatesResult.observe(this, Observer {
            lifecycleScope.launch(Dispatchers.Main) {
                mainViewModel.getCurrentWeather(it.lat, it.lon)
                {
                    progressIndicator.visibility = INVISIBLE
                    viewPager.visibility = VISIBLE
                    screenDataValidation()
                }
                mainViewModel.getForecast(it.lat, it.lon)
            }
        })
    }

    private fun prepareViewPager() {
        val fragmentList = arrayListOf(
            WeatherFragment.newInstance(),
            ForecastFragment.newInstance()
        )
        val tabTitleArray = arrayOf(getString(R.string.Weather), getString(R.string.Forecast))

        viewPager.adapter = ViewPagerAdapter(this, fragmentList)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()
    }

    private fun search() {
        viewPager.visibility = INVISIBLE
        plug.visibility = INVISIBLE
        progressIndicator.visibility = VISIBLE
        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.getCoordinates(inputField.editText?.text.toString())
        }
    }

    private fun screenDataValidation() {
        val inputResult = inputField.editText?.text.toString()
        if (inputResult == "") {
            viewPager.visibility = INVISIBLE
            plug.visibility = VISIBLE
            progressIndicator.visibility = INVISIBLE
        } else {
            plug.visibility = INVISIBLE
        }
    }

    private fun hideKeyboardAndClearFocus(editText: EditText?) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText?.windowToken, 0)
        editText?.clearFocus()
    }

    override fun onStop() {
        super.onStop()
        val inputCityResult = inputField.editText?.text.toString()
        mainViewModel.saveDataInPrefs(PREFS_CITY_KEY, inputCityResult)
    }
}