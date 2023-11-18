package com.nqs.weatherapp

data class FiveDayForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ThreeHourForecast>,
    val message: Int
)