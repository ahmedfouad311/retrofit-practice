package com.example.weather_app

import com.example.weather_app.models.BaseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeather(@Query("q") cityName: String,
                   @Query("appid") apiKey: String)
    :Call<BaseModel>
}