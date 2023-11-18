package com.nqs.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import com.google.android.material.appbar.MaterialToolbar
import com.nqs.weatherapp.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //fetch current weather data
        fetchCurrentWeatherData("puyallup")

        //use search bar
        SearchCity()
    }

    private fun SearchCity(){
        val searchView = binding.searchBar
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    fetchCurrentWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }
    private fun fetchCurrentWeatherData(cityName:String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(CurrentWeatherApi::class.java)

        val response = retrofit.getCurrentWeatherData(cityName, "API KEY", "imperial")
        response.enqueue(object : Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    //get data from api
                    val temperature = responseBody.main.temp.toString()
                    val weatherDescription = responseBody.weather[0].description
                    val pressure = responseBody.main.pressure.toString()
                    val humidity = responseBody.main.humidity.toString()
                    val wind = responseBody.wind.speed.toString()

                    //get icon from api
                    val iconCode = responseBody.weather[0].icon
                    val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"
                    val imageView = findViewById<ImageView>(R.id.day_one_weather_icon)

                    //display the icon
                    Picasso.get().load(url).into(imageView)

                    //bind the data with the views
                    binding.dayOneDescription.text = weatherDescription
                    binding.dayOneTemp.text = "Temp: $temperature"
                    binding.pressureText.text = pressure
                    binding.humidityText.text = humidity
                    binding.windText.text = wind
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}
