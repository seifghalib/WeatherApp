package com.example.cubicdemoapp.di

import com.example.cubicdemoapp.api.OpenWeatherApi
import com.example.cubicdemoapp.data.WeatherResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val openWeatherApi: OpenWeatherApi){

    suspend fun getWeatherUpdate(): WeatherResponse {
        return openWeatherApi.getWeather()
    }
}