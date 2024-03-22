package com.example.weatherapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapp.data.network.RetrofitHelper
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.data.repository.PrefsRepository
import com.example.weatherapp.data.repository.PrefsRepositoryImpl
import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
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
    fun providesWeatherRepository(weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }

    @Provides
    @Singleton
    fun providesRequestsApi(): WeatherApi {
        return RetrofitHelper.getInstance().create(WeatherApi::class.java)
    }
}