package com.example.weatherapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.weatherapi.com/v1/"
const val API_KEY = "9774771347ed4029bae184250220404"

interface WeatherInterface {
    @GET("current.json?key=$API_KEY")
    fun getWeatherData(@Query("q") country: String) : Call<Weather>
}

object WeatherService {
    val weatherInstance: WeatherInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherInstance = retrofit.create(WeatherInterface::class.java)
    }
}