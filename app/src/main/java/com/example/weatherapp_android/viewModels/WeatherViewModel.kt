package com.example.weatherapp_android.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp_android.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    val weatherLiveData get() = weatherRepository.weatherLiveData

    fun getWeatherData(apiKey: String, location: String) {
        viewModelScope.launch {
            weatherRepository.getLocationForecast(apiKey, location)
        }
    }
}