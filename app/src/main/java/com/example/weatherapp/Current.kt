package com.example.weatherapp

data class Current(
    val condition: Condition,
    val humidity: Float,
    val is_day: Float,
    val pressure_in: Double,
    val temp_c: Float,
    val wind_mph: Double
)