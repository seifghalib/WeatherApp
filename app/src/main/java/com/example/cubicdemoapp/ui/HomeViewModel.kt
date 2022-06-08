package com.example.cubicdemoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cubicdemoapp.data.WeatherResponse
import com.example.cubicdemoapp.di.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherLiveData = MutableLiveData<WeatherResponse>()
    val weatherResponseLiveData : LiveData<WeatherResponse> = _weatherLiveData

    init {
        makeWeatherCall()
    }

    private fun makeWeatherCall() = viewModelScope.launch(Dispatchers.IO){
        _weatherLiveData.postValue(weatherRepository.getWeatherUpdate())
    }
}