package com.example.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.data.model.currentweather.WeatherResult
import com.example.weatherapp.data.model.forecast.City
import com.example.weatherapp.data.model.forecast.Coord
import com.example.weatherapp.data.model.forecast.FiveDayForecast
import com.example.weatherapp.data.repository.PrefsRepository
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.presentation.viewmodel.MainViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val weatherRepositoryMock: WeatherRepository = mock()
    private val prefsRepositoryMock: PrefsRepository = mock()

    private lateinit var viewModel: MainViewModel

    private val keyTestResult = "keyTestValue"
    private val valueTestValue = "valueTestValue"

    @Before
    fun setup() {
        viewModel = MainViewModel(weatherRepositoryMock, prefsRepositoryMock)
    }

    @Test
    fun `test getCoordinates`() = runBlocking {

        val city = "testCity"
        val coordinates = Coord(1.0, 1.0)

        `when`(weatherRepositoryMock.getLocationCoordinates(city)).thenReturn(coordinates)

        viewModel.getCoordinates(city)

        assertEquals(coordinates, viewModel.coordinatesResult.value)
    }

    @Test
    fun `test getCurrentWeather`() = runBlocking {

        val lat = 0.0
        val lon = 0.0

        val weatherResult = WeatherResult(
            "main",
            "description",
            1.0,
            1,
            1.0,
            1
        )

        `when`(weatherRepositoryMock.getCurrentWeather(lat, lon)).thenReturn(weatherResult)

        viewModel.getCurrentWeather(lat, lon) {

        }

        assertEquals(weatherResult, viewModel.currentWeatherResult.value)
    }

    @Test
    fun `test getForecast`() = runBlocking {

        val lat = 0.0
        val lon = 0.0

        val forecastResult = FiveDayForecast(
            City(
                (Coord(
                    1.0,
                    1.0
                )),
                "country", 1, "name", 100, 200, 300, 400
            ),
            1, "cod", emptyList(), 1
        )

        `when`(weatherRepositoryMock.getForecast(lat, lon)).thenReturn(forecastResult)

        viewModel.getForecast(lat, lon)

        assertEquals(forecastResult, viewModel.forecastResult.value)
    }

    @Test
    fun `test getCityFromPrefs`() {
        val city = "testCity"
        `when`(prefsRepositoryMock.getCity()).thenReturn(city)
        viewModel.getCityFromPrefs()
        assertEquals(city, prefsRepositoryMock.getCity())
    }

    @Test
    fun `test saveDataInPrefs`() {
        viewModel.saveDataInPrefs(keyTestResult, valueTestValue)
        Mockito.verify(prefsRepositoryMock).saveDataInPrefs(keyTestResult, valueTestValue)
    }
}