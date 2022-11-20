package com.example.weather_app.repo

import com.example.weather_app.WeatherApi
import com.example.weather_app.models.BaseModel
import com.example.weather_app.network.NetworkConstants.API_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepo(private val service: WeatherApi) {


    fun getWeatherData(
        cityName: String,
        apiKey: String = API_KEY,
        onSuccess: (model: BaseModel?) -> Unit,
        onFailure: (t: Throwable) -> Unit
    ) {
        service.getWeather(cityName = cityName, apiKey = apiKey)
            .enqueue(object : Callback<BaseModel> {
                override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                    onSuccess.invoke(response.body())
                }

                override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                    onFailure.invoke(t)
                }

            })
    }
}