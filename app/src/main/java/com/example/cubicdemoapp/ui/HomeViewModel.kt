package com.example.cubicdemoapp.ui

import androidx.lifecycle.*
import com.example.cubicdemoapp.di.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    fun getWeather() = weatherRepository.getWeather()
        .flowOn(Dispatchers.IO)
        .shareIn(
            scope = viewModelScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed()
        )
        .asLiveData()
}