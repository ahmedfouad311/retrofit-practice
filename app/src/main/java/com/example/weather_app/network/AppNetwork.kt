package com.example.weather_app.network

import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

class AppNetwork private constructor(val retrofit: Retrofit) {

    companion object {
        lateinit var retrofit: Retrofit

        private lateinit var instance: AppNetwork

        fun newInstance(baseUrl: String = NetworkConstants.BASE_URL): AppNetwork {
            if (this::retrofit.isInitialized.not())
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(
                        OkHttpClient.Builder()
                            .addInterceptor(
                                LoggingInterceptor.Builder()
                                    .setLevel(Level.BODY)
                                    .build()
                            ).build()
                    )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            if (this::instance.isInitialized.not())
                instance = AppNetwork(this.retrofit)
            return instance
        }
    }

    fun <T : Any> createService(clazz: Class<T>): T = retrofit.create(clazz)
}