package com.example.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private val apiKey: String = "238665c84ba9527bfe464eeb30db5dfe"
    val model: WeatherModel = WeatherModel()
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.weatherTv)
        getWeather()
    }

    fun getWeather(): Unit{
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api: WeatherApi = retrofit.create(WeatherApi::class.java)
        val response: Call<WeatherModel> = api.getWeather("Egypt", apiKey)

        response.enqueue(object : Callback<WeatherModel>{
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                if(response.code() == 404){
//                    tv.text = "Invalid City"
                    Log.e("Code 404", "Invalid City")
                } else if(!(response.isSuccessful)){
//                    tv.text = response.code().toString()
                    Log.e("unSuccessful response", response.code().toString())
                }
                var retrievedData: WeatherModel? = response.body()
                var apiTemp = retrievedData?.temp
                var fixedTemp = apiTemp?.toInt()

                Log.e("Temp", fixedTemp.toString() + "C")

                runOnUiThread {
                    tv.text = "$fixedTemp C"
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                tv.text = t.message
            }

        })
    }
}