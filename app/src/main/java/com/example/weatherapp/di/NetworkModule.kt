package com.example.weatherapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapp.network.RetrofitHelper
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.repository.PrefsRepository
import com.example.weatherapp.repository.PrefsRepositoryImpl
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
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PrefsRepositoryImpl.PREFS_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesPrefsRepository(sharedPreferences: SharedPreferences): PrefsRepository =
        PrefsRepositoryImpl(sharedPreferences)

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