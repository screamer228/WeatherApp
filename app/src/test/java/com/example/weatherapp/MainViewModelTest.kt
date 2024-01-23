package com.example.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.model.currentweather.WeatherResult
import com.example.weatherapp.model.forecast.City
import com.example.weatherapp.model.forecast.Coord
import com.example.weatherapp.model.forecast.FiveDayForecast
import com.example.weatherapp.repository.PrefsRepository
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.viewmodel.MainViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val weatherRepositoryMock: WeatherRepository = mock()
    private val prefsRepositoryMock: PrefsRepository = mock()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup(){
        viewModel = MainViewModel(weatherRepositoryMock, prefsRepositoryMock)
    }

    @Test
    fun `test getCoordinates`() = runBlocking{

        val city = "testCity"
        val coordinates = Coord(1.0, 1.0)

        `when`(weatherRepositoryMock.getLocationCoordinates(city)).thenReturn(coordinates)

        assertEquals(coordinates, weatherRepositoryMock.getLocationCoordinates(city))
    }

    @Test
    fun `test getCurrentWeather`() = runBlocking {

        val lat = 0.0
        val lon = 0.0

        val weatherResult = WeatherResult("main", "description", 1.0, 1, 1.0, 1)

        `when`(weatherRepositoryMock.getCurrentWeather(lat, lon)).thenReturn(weatherResult)

        assertEquals(weatherResult, weatherRepositoryMock.getCurrentWeather(lat, lon))
    }

    @Test
    fun `test getForecast`() = runBlocking {

        val lat = 0.0
        val lon = 0.0

        val forecastResult = FiveDayForecast(
            City((Coord(1.0, 1.0)), "country", 1, "name", 100, 200, 300,400),
            1, "cod", emptyList(), 1)

        `when`(weatherRepositoryMock.getForecast(lat, lon)).thenReturn(forecastResult)

        assertEquals(forecastResult, weatherRepositoryMock.getForecast(lat, lon))
    }

}