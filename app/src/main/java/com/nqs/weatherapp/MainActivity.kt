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
import java.text.SimpleDateFormat
import java.util.Date

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
        fetchFiveDayForecastData("puyallup")

        //use search bar
        SearchCity()
    }

    private fun fetchFiveDayForecastData(cityName:String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(FiveDayForecastApi::class.java)

        val response = retrofit.getFiveDayForecastData(cityName, "91a0c7771716b8912caf89842ed9b946", "imperial")
        response.enqueue(object : Callback<FiveDayForecast>{
            override fun onResponse(
                call: Call<FiveDayForecast>,
                response: Response<FiveDayForecast>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    var lastIndex = 0

                    //insert code here
                    val simpleDateFormat = SimpleDateFormat("yyyy/M/dd")
                    val currentDate = simpleDateFormat.format(Date())


                    //check list to find the next day one
                    for (i in lastIndex..<responseBody.list.size){
                        if(responseBody.list[i].dt_txt.contains("$currentDate 12:00:00")){
                            lastIndex = i + 1
                            break
                        }
                    }
                    //day two
                    for (i in lastIndex..<responseBody.list.size) {
                        if (responseBody.list[i].dt_txt.contains("12:00:00")) {
                            //get the icon code
                            val dayTwoWeatherIcon = responseBody.list[i].weather[0].icon
                            //create the url with the icon code
                            val url = "https://openweathermap.org/img/wn/$dayTwoWeatherIcon@2x.png"
                            val imageView = findViewById<ImageView>(R.id.day_two_weather_icon)
                            Picasso.get().load(url).into(imageView)

                            //load the temp
                            val dayTwoTemp = responseBody.list[i].main.temp.toString()
                            binding.dayTwoTemp.text = dayTwoTemp
                            lastIndex = i + 1
                            break
                        }
                    }
                    //day three
                    for (i in lastIndex..<responseBody.list.size) {
                        if (responseBody.list[i].dt_txt.contains("12:00:00")) {
                            //get the icon code
                            val dayThreeWeatherIcon = responseBody.list[i].weather[0].icon
                            //create the url with the icon code
                            val url = "https://openweathermap.org/img/wn/$dayThreeWeatherIcon@2x.png"
                            val imageView = findViewById<ImageView>(R.id.day_three_weather_icon)
                            Picasso.get().load(url).into(imageView)

                            //load the temp
                            val dayThreeTemp = responseBody.list[i].main.temp.toString()
                            binding.dayThreeTemp.text = dayThreeTemp
                            lastIndex = i + 1
                            break
                        }
                    }
                    //day four
                    for (i in lastIndex..<responseBody.list.size) {
                        if (responseBody.list[i].dt_txt.contains("12:00:00")) {
                            //get the icon code
                            val dayFourWeatherIcon = responseBody.list[i].weather[0].icon
                            //create the url with the icon code
                            val url = "https://openweathermap.org/img/wn/$dayFourWeatherIcon@2x.png"
                            val imageView = findViewById<ImageView>(R.id.day_four_weather_icon)
                            Picasso.get().load(url).into(imageView)

                            //load the temp
                            val dayFourTemp = responseBody.list[i].main.temp.toString()
                            binding.dayFourTemp.text = dayFourTemp
                            lastIndex = i + 1
                            break
                        }
                    }
                    //day five
                    for (i in lastIndex..<responseBody.list.size) {
                        if (responseBody.list[i].dt_txt.contains("12:00:00")) {
                            //get the icon code
                            val dayFiveWeatherIcon = responseBody.list[i].weather[0].icon
                            //create the url with the icon code
                            val url = "https://openweathermap.org/img/wn/$dayFiveWeatherIcon@2x.png"
                            val imageView = findViewById<ImageView>(R.id.day_five_weather_icon)
                            Picasso.get().load(url).into(imageView)

                            //load the temp
                            val dayFiveTemp = responseBody.list[i].main.temp.toString()
                            binding.dayFiveTemp.text = dayFiveTemp
                            lastIndex = i + 1
                            break
                        }
                    }
                    //day six
                    for (i in lastIndex..<responseBody.list.size) {
                        if (responseBody.list[i].dt_txt.contains("12:00:00")) {
                            //get the icon code
                            val daySixWeatherIcon = responseBody.list[i].weather[0].icon
                            //create the url with the icon code
                            val url = "https://openweathermap.org/img/wn/$daySixWeatherIcon@2x.png"
                            val imageView = findViewById<ImageView>(R.id.day_six_weather_icon)
                            Picasso.get().load(url).into(imageView)

                            //load the temp
                            val daySixTemp = responseBody.list[i].main.temp.toString()
                            binding.daySixTemp.text = daySixTemp
                            lastIndex = i + 1
                            break
                        }
                    }
                }
            }

            override fun onFailure(call: Call<FiveDayForecast>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
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

        val response = retrofit.getCurrentWeatherData(cityName, "91a0c7771716b8912caf89842ed9b946", "imperial")
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
                    binding.dayOneTemp.text = temperature
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
