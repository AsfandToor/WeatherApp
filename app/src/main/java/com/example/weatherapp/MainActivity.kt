package com.example.weatherapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import com.example.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    lateinit var weatherData: Weather
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            if (binding.inputPlace.text.toString().isNotEmpty()) {
                var country = binding.inputPlace.text.toString().trim()
                country = country.replace(" ", "+", true)
                getWeatherData(country)
            }
            else {
                Toast.makeText(applicationContext, "Please Enter City/Country", Toast.LENGTH_SHORT).show()
                binding.inputPlace.text.clear()
            }
        }
    }

    private fun getWeatherData(country: String) {
        val weather: Call<Weather> = WeatherService.weatherInstance.getWeatherData(country)
        weather.enqueue(object: Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                try {
                    weatherData = response.body()!!
                    binding.country.setText(weatherData.location.name)
                    binding.weather.setText(weatherData.current.condition.text)
                    binding.temperature.setText(weatherData.current.temp_c.toString())
                    binding.pressure.setText(weatherData.current.pressure_in.toString())
                    binding.wind.setText(weatherData.current.wind_mph.toString())
                    binding.humidity.setText(weatherData.current.humidity.toString())
                    binding.inputPlace.text.clear()
                }
                catch (E: Exception) {
                    Toast.makeText(applicationContext,"No such City/Country Exists", Toast.LENGTH_SHORT).show()
                    binding.inputPlace.text.clear()
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d("Retro", "Failed", t)
            }
        })
    }
}