package com.example.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.weather_app.models.BaseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private val apiKey: String = "238665c84ba9527bfe464eeb30db5dfe"
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.weatherTv)
        getWeather()
    }

    private fun getWeather(){
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api: WeatherApi = retrofit.create(WeatherApi::class.java)
        val response: Call<BaseModel> = api.getWeather("Egypt", apiKey)

        response.enqueue(object : Callback<BaseModel>{
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                if(response.code() == 404){
                    runOnUiThread {
                        tv.text = "Invalid City"
                    }
                } else if(!(response.isSuccessful)){
                    runOnUiThread {
                        tv.text = "${response.code()}"
                    }
                }
                var retrievedData: BaseModel? = response.body()
                var apiTemp = retrievedData?.main?.temp?.toInt()
                var fixedTemp = apiTemp
                runOnUiThread {
                    tv.text = "$fixedTemp"
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                runOnUiThread {
                    tv.text = t.message
                }
            }


        })
    }
}