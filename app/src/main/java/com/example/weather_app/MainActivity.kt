package com.example.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.weather_app.models.BaseModel
import com.example.weather_app.network.AppNetwork
import com.example.weather_app.network.AppNetwork.Companion.retrofit
import com.example.weather_app.repo.AppRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val viewmodel: MainViewModel by viewModels()

    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById(R.id.weatherTv)

        viewmodel.getWeather()

        viewmodel.result.observe(this) {
            tv.text = it.main.temp.toInt().toString()
        }

        viewmodel.error.observe(this) {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

}