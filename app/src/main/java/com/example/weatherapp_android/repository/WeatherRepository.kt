package com.example.weatherapp_android.repository

import androidx.lifecycle.MutableLiveData
import com.example.weatherapp_android.api.WeatherAPI
import com.example.weatherapp_android.models.WeatherModel
import com.example.weatherapp_android.utils.NetworkResult
import timber.log.Timber
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherAPI: WeatherAPI) {

    private val _weatherLiveData = MutableLiveData<NetworkResult<WeatherModel>>()
    val weatherLiveData get() = _weatherLiveData

    suspend fun getLocationForecast(apiKey: String, location: String) {

        _weatherLiveData.postValue(NetworkResult.Loading())
        val response = weatherAPI.getWeather(apiKey, location)
        Timber.e(response.toString())

        if (response.isSuccessful && response.body() != null) {
            _weatherLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _weatherLiveData.postValue(
                NetworkResult.Error(
                    response.errorBody()!!.charStream().readText()
                )
            )
        } else {
            _weatherLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}