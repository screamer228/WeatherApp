package com.example.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding

    private val mainViewModel : MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.currentWeatherResult.observe(viewLifecycleOwner, Observer {
            binding?.weatherTempValueTv?.text = it.temp.toString()
            binding?.weatherDescriptionValueTv?.text = it.description.toString()
            binding?.weatherHumidityValueTv?.text = it.humidity.toString()
            binding?.weatherWindSpeedValueTv?.text = it.windSpeed.toString()
            binding?.weatherPressureValueTv?.text = it.pressure.toString()
        })
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }
}