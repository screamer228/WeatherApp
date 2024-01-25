package com.example.weatherapp

import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.WeatherRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class WeatherRepositoryImplTest {

    private val weatherApiMock: WeatherApi = mock()

    private lateinit var subject: WeatherRepositoryImpl

    @Before
    fun setup(){
        subject = WeatherRepositoryImpl(weatherApiMock)
    }

    @Test
    fun getLocationCoordinates_success() : Unit = runBlocking {

    }

}