package com.example.cubicdemoapp.api

import com.example.cubicdemoapp.BuildConfig
import com.example.cubicdemoapp.data.WeatherResponse
import retrofit2.http.GET

interface OpenWeatherApi {

    companion object{
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val ICON_URL = "https://openweathermap.org/img/wn/%s@2x.png"
        const val OPEN_CLIENT_ID = BuildConfig.OPENWEATHER_API_KEY
        const val ZIP_CODE = 27407
    }

    @GET("weather?zip=$ZIP_CODE,us&appid=$OPEN_CLIENT_ID&units=imperial")
    suspend fun getWeather() : WeatherResponse
}