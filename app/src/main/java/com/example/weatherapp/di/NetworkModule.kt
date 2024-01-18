package com.example.weatherapp.di

import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesWeatherRepository(weatherApi: WeatherApi) : WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }

    @Provides
    @Singleton
    fun providesRequestsApi() : WeatherApi{
        return RetrofitHelper.getInstance().create(WeatherApi::class.java)
    }
}