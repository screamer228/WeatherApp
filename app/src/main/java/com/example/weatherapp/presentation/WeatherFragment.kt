package com.example.weatherapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_CLEAR
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_CLOUDS
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_DRIZZLE
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_RAIN
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_SNOW
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_THUNDERSTORM
import com.example.weatherapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.currentWeatherResult.observe(viewLifecycleOwner) {
            val outputTemp = String.format("%.1f", it.temp)
            val outputWindSpeed = String.format("%.1f", it.windSpeed)
            binding?.weatherTempValueTv?.text = getString(R.string.Celsius, outputTemp)
            binding?.weatherDescriptionValueTv?.text = it.description
            binding?.weatherHumidityValueTv?.text = "${it.humidity} %"
            binding?.weatherWindSpeedValueTv?.text =
                getString(R.string.metres_per_sec, outputWindSpeed)
            binding?.weatherPressureValueTv?.text = it.pressure.toString()

            val weatherType = it.main

            when (weatherType) {
                WEATHER_TYPE_CLEAR -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            WeatherType.Clear.imageId
                        )
                    )
                }

                WEATHER_TYPE_CLOUDS -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            WeatherType.Clouds.imageId
                        )
                    )
                }

                WEATHER_TYPE_RAIN, WEATHER_TYPE_DRIZZLE -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            WeatherType.Rain.imageId
                        )
                    )
                }

                WEATHER_TYPE_SNOW -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            WeatherType.Snow.imageId
                        )
                    )
                }

                WEATHER_TYPE_THUNDERSTORM -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            WeatherType.Thunderstorm.imageId
                        )
                    )
                }

                else -> {
                    binding?.weatherImage?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            WeatherType.Fog.imageId
                        )
                    )
                }
            }
        }
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }
}