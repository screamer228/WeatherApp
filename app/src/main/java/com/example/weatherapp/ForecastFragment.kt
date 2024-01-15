package com.example.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FragmentForecastBinding
import com.example.weatherapp.model.forecast.ForecastResult

class ForecastFragment : Fragment() {

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding?.forecastRecycler?.layoutManager = layoutManager
        val forecastResultList = listOf<ForecastResult>(
            ForecastResult("testDate", "testTemp", "testDescription", "testMain"),
            ForecastResult("testDate2", "testTemp2", "testDescription2", "testMain2"),
            ForecastResult("testDate3", "testTemp3", "testDescription3", "testMain3"),
            ForecastResult("testDate2", "testTemp2", "testDescription2", "testMain2"),
            ForecastResult("testDate2", "testTemp2", "testDescription2", "testMain2"),
            ForecastResult("testDate2", "testTemp2", "testDescription2", "testMain2"),
            ForecastResult("testDate2", "testTemp2", "testDescription2", "testMain2"),
            ForecastResult("testDate2", "testTemp2", "testDescription2", "testMain2"),
            ForecastResult("testDate2", "testTemp2", "testDescription2", "testMain2")
        )
        adapter = ForecastAdapter(requireContext(), forecastResultList)
        binding?.forecastRecycler?.adapter = adapter
    }

    companion object {
        fun newInstance() = ForecastFragment()
    }
}