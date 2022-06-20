package com.example.cubicdemoapp.di

import com.example.cubicdemoapp.api.OpenWeatherApi
import com.example.cubicdemoapp.utils.toResultFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(private val openWeatherApi: OpenWeatherApi){

    fun getWeather() = toResultFlow {
        openWeatherApi.getWeather()
    }
}