package com.example.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemForecastBinding
import com.example.weatherapp.model.forecast.ForecastResult
import com.example.weatherapp.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_CLEAR
import com.example.weatherapp.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_CLOUDS
import com.example.weatherapp.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_DRIZZLE
import com.example.weatherapp.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_RAIN
import com.example.weatherapp.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_SNOW
import com.example.weatherapp.repository.WeatherRepositoryImpl.Companion.WEATHER_TYPE_THUNDERSTORM
import java.text.SimpleDateFormat
import java.util.Locale

class ForecastAdapter(private val fragmentContext: Context, private val forecastList: List<ForecastResult>)
    : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

        class ViewHolder(private val binding: ItemForecastBinding, private val context: Context)
            : RecyclerView.ViewHolder(binding.root){
                fun bindItem(forecast: ForecastResult){
                    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val outputDateFormat = SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())

                    val date = inputDateFormat.parse(forecast.date)
                    val outputDate = date?.let { outputDateFormat.format(it) }

                    val outputTemp = String.format("%.1f", forecast.temp)
                    binding.itemRecyclerDate.text = "$outputDate"
                    binding.itemRecyclerTemp.text = "$outputTemp \u2103"
                    binding.itemRecyclerDescription.text = forecast.description
                    when (forecast.main) {
                        WEATHER_TYPE_CLEAR -> {
                            binding.itemRecyclerImage.setImageDrawable(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.clear_sky
                                )
                            )
                        }
                        WEATHER_TYPE_CLOUDS -> {
                            binding.itemRecyclerImage.setImageDrawable(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.clouds
                                )
                            )
                        }
                        WEATHER_TYPE_RAIN, WEATHER_TYPE_DRIZZLE -> {
                            binding.itemRecyclerImage.setImageDrawable(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.rain
                                )
                            )
                        }
                        WEATHER_TYPE_SNOW -> {
                            binding.itemRecyclerImage.setImageDrawable(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.snow
                                )
                            )
                        }
                        WEATHER_TYPE_THUNDERSTORM -> {
                            binding.itemRecyclerImage.setImageDrawable(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.thunderstorm
                                )
                            )
                        }
                        else -> {
                            binding.itemRecyclerImage.setImageDrawable(
                                ContextCompat.getDrawable(
                                    context,
                                    R.drawable.fog
                                )
                            )
                        }
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, fragmentContext)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = forecastList[position]
        holder.bindItem(forecast)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}