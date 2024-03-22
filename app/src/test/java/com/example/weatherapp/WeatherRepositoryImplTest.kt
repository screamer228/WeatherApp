package com.example.weatherapp

import com.example.weatherapp.data.model.currentweather.CurrentWeather
import com.example.weatherapp.data.model.currentweather.Main
import com.example.weatherapp.data.model.currentweather.Sys
import com.example.weatherapp.data.model.forecast.City
import com.example.weatherapp.data.model.forecast.Clouds
import com.example.weatherapp.data.model.forecast.Coord
import com.example.weatherapp.data.model.forecast.FiveDayForecast
import com.example.weatherapp.data.model.forecast.Forecast
import com.example.weatherapp.data.model.forecast.MainForecast
import com.example.weatherapp.data.model.forecast.Rain
import com.example.weatherapp.data.model.forecast.SysForecast
import com.example.weatherapp.data.model.forecast.Weather
import com.example.weatherapp.data.model.forecast.Wind
import com.example.weatherapp.data.model.geocoding.Geocoding
import com.example.weatherapp.data.model.geocoding.GeocodingItem
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.APP_ID
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.COUNT
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.LANG
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.LIMIT
import com.example.weatherapp.data.repository.WeatherRepositoryImpl.Companion.METRIC
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class WeatherRepositoryImplTest {

    private val lat = 37.7790262
    private val lon = -122.419906

    private val weatherApiMock: WeatherApi = mock()

    private lateinit var subject: WeatherRepositoryImpl

    @Before
    fun setup() {
        subject = WeatherRepositoryImpl(weatherApiMock)
    }

    @Test
    fun getLocationCoordinates_success(): Unit = runBlocking {
        val city = "London"
        val expectedCoordinates =
            Coord(37.7790262, -122.419906)
        val location = Geocoding()

        location.add(
            GeocodingItem(
                "US",
                37.7790262,
                null,
                -122.419906,
                "San Francisco",
                "California"
            )
        )
        location.add(
            GeocodingItem(
                "US",
                40.7127281,
                null,
                -74.0060152,
                "New York Country",
                "New York"
            )
        )
        val mockResponse = Response.success(location)

        `when`(weatherApiMock.getGeocoding(city, LIMIT, APP_ID)).thenReturn(mockResponse)

        val result = subject.getLocationCoordinates(city)

        assertEquals(expectedCoordinates.lat, result.lat)
        assertEquals(expectedCoordinates.lon, result.lon)
    }

    @Test
    fun getCurrentWeather_success(): Unit = runBlocking {
        val location = CurrentWeather(
            "base",
            Clouds(1),
            1,
            Coord(1.0, 1.0),
            1,
            1,
            Main(1.0, 1, 1, 1, 1, 1.0, 1.0, 1.0),
            "name",
            Sys("country", 1, 1, 1, 1),
            1,
            1,
            listOf(
                Weather(
                    "description",
                    "icon",
                    1,
                    "main"
                )
            ),
            Wind(1, 1.0, 1.0)
        )
        val mockResponse = Response.success(location)

        `when`(weatherApiMock.getCurrentWeather(lat, lon, APP_ID, METRIC, LANG)).thenReturn(
            mockResponse
        )

        val result = subject.getCurrentWeather(lat, lon)

        assertEquals("main", result.main)
        assertEquals("description", result.description)
    }

    @Test
    fun getForecast_success(): Unit = runBlocking {
        val expectedCity = City(
            Coord(lat, lon),
            "US",
            1,
            "San Francisco",
            1,
            1,
            1,
            1
        )
        val expectedForecast = FiveDayForecast(
            expectedCity, 9, "cod",
            listOf(
                Forecast(
                    Clouds(1),
                    1,
                    "dt",
                    MainForecast(
                        1.0,
                        1,
                        1,
                        1,
                        1,
                        1.0,
                        1.0,
                        1.0,
                        1.0
                    ),
                    1.0,
                    Rain(1.1),
                    SysForecast("pod"),
                    1,
                    listOf(
                        Weather(
                            "description",
                            "icon",
                            1,
                            "main"
                        )
                    ),
                    Wind(1, 1.1, 1.1)
                )
            ), 0
        )

        val mockResponse = Response.success(expectedForecast)

        `when`(weatherApiMock.getForecast(lat, lon, APP_ID, COUNT, METRIC, LANG)).thenReturn(
            mockResponse
        )

        val actualForecast = subject.getForecast(lat, lon)

        assertEquals(expectedForecast, actualForecast)
    }
}