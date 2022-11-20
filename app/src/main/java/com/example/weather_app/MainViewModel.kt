package com.example.weather_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_app.models.BaseModel
import com.example.weather_app.network.AppNetwork
import com.example.weather_app.repo.AppRepo

const val city = "Egypt"

class MainViewModel() : ViewModel() {

    private var appRepo: AppRepo

    init {
        val api: WeatherApi = AppNetwork.newInstance().createService(WeatherApi::class.java)
        appRepo = AppRepo(service = api)
    }

    private val _result: MutableLiveData<BaseModel> by lazy {
        MutableLiveData()
    }

    val result: LiveData<BaseModel> = _result

    private val _error: MutableLiveData<Throwable> by lazy {
        MutableLiveData()
    }

    val error: LiveData<Throwable> = _error

    fun getWeather() {
        appRepo.getWeatherData(cityName = city, onSuccess = {
            _result.value = it
        }, onFailure = {
            _error.value = it
        })
    }
}