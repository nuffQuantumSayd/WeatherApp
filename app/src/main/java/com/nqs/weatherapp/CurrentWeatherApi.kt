package com.nqs.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherApi {
    @GET("weather")
    fun getCurrentWeatherData(
        @Query("q") city:String,
        @Query("appid") appid:String,
        @Query("units") units:String
    ) : Call<CurrentWeather>
}