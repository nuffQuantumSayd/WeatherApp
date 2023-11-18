package com.nqs.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FiveDayForecastApi {
    @GET("forecast")
    fun getFiveDayForecastData(
        @Query("q") city:String,
        @Query("appid") appid:String,
        @Query("units") units:String
    ) : Call<FiveDayForecast>
}