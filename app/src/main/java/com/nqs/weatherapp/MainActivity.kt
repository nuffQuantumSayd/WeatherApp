package com.nqs.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.appbar.MaterialToolbar
import com.nqs.weatherapp.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    var currentSettings = CurrentSetting("Puyallup", "imperial")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //fetch current weather data
        fetchCurrentWeatherData(currentSettings.cityName, currentSettings.unitOfMeasure)
        fetchFiveDayForecastData(currentSettings.cityName, currentSettings.unitOfMeasure)

        //use search bar
        SearchCity()

        //On click metric change
        changeTempUnit()
    }

    private fun changeTempUnit() {
        val temperatureButton = findViewById<Button>(R.id.button)
        temperatureButton.setOnClickListener {
            if (temperatureButton.text == "F"){
                currentSettings.unitOfMeasure = "metric"
                fetchCurrentWeatherData(currentSettings.cityName, currentSettings.unitOfMeasure)
                fetchFiveDayForecastData(currentSettings.cityName, currentSettings.unitOfMeasure)
                temperatureButton.text = "C"
            }
            else {
                currentSettings.unitOfMeasure = "imperial"
                fetchCurrentWeatherData(currentSettings.cityName, currentSettings.unitOfMeasure)
                fetchFiveDayForecastData(currentSettings.cityName, currentSettings.unitOfMeasure)
                temperatureButton.text = "F"
            }
        }
    }

    private fun setBackground(iconCode:String){
        var activityMain = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main_view)

        if (iconCode.contains("02") || iconCode.contains("03") || iconCode.contains("04")){
            activityMain.setBackgroundResource(R.drawable.clouds_background)
        }
        else if (iconCode.contains("01")){
            activityMain.setBackgroundResource(R.drawable.sunny_background)
        }
        else if (iconCode.contains("09") || iconCode.contains("10") || iconCode.contains("11")){
            activityMain.setBackgroundResource(R.drawable.rain_background)
        }
        else if (iconCode.contains("13")){
            activityMain.setBackgroundResource(R.drawable.snow_background)
        }
        else{
            activityMain.setBackgroundResource(R.drawable.mist_background)
        }

    }
    private fun fetchFiveDayForecastData(cityName:String, unit:String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(FiveDayForecastApi::class.java)

        val response = retrofit.getFiveDayForecastData(cityName, "91a0c7771716b8912caf89842ed9b946", unit)
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
                    val noon = "12:00:00"

                    //check list to find the next day one
                    for (i in lastIndex..<responseBody.list.size){
                        if(responseBody.list[i].dt_txt.contains("$currentDate $noon")){
                            lastIndex = i + 1
                            break
                        }
                    }
                    //day two
                    for (i in lastIndex..<responseBody.list.size) {
                        if (responseBody.list[i].dt_txt.contains(noon)) {
                            //get the icon code
                            val dayTwoWeatherIcon = responseBody.list[i].weather[0].icon
                            //create the url with the icon code
//                            val url = "https://openweathermap.org/img/wn/$dayTwoWeatherIcon@2x.png"
                            val imageView = findViewById<ImageView>(R.id.day_two_weather_icon)
//                            Picasso.get().load(url).into(imageView)
                            setIconImage(dayTwoWeatherIcon, imageView, false)

                            //load the temp
                            val dayTwoTemp = responseBody.list[i].main.temp.toString()
                            binding.dayTwoTemp.text = dayTwoTemp
                            lastIndex = i + 1
                            break
                        }
                    }
                    //day three
                    for (i in lastIndex..<responseBody.list.size) {
                        if (responseBody.list[i].dt_txt.contains(noon)) {
                            //get the icon code
                            val dayThreeWeatherIcon = responseBody.list[i].weather[0].icon
                            //create the url with the icon code
//                            val url = "https://openweathermap.org/img/wn/$dayThreeWeatherIcon@2x.png"
                            val imageView = findViewById<ImageView>(R.id.day_three_weather_icon)
//                            Picasso.get().load(url).into(imageView)

                            setIconImage(dayThreeWeatherIcon, imageView, false)

                            //load the temp
                            val dayThreeTemp = responseBody.list[i].main.temp.toString()
                            binding.dayThreeTemp.text = dayThreeTemp
                            lastIndex = i + 1
                            break
                        }
                    }
                    //day four
                    for (i in lastIndex..<responseBody.list.size) {
                        if (responseBody.list[i].dt_txt.contains(noon)) {
                            //get the icon code
                            val dayFourWeatherIcon = responseBody.list[i].weather[0].icon
                            //create the url with the icon code
//                            val url = "https://openweathermap.org/img/wn/$dayFourWeatherIcon@2x.png"
                            val imageView = findViewById<ImageView>(R.id.day_four_weather_icon)
//                            Picasso.get().load(url).into(imageView)

                            setIconImage(dayFourWeatherIcon, imageView, false)

                            //load the temp
                            val dayFourTemp = responseBody.list[i].main.temp.toString()
                            binding.dayFourTemp.text = dayFourTemp
                            lastIndex = i + 1
                            break
                        }
                    }
                    //day five
                    for (i in lastIndex..<responseBody.list.size) {
                        if (responseBody.list[i].dt_txt.contains(noon)) {
                            //get the icon code
                            val dayFiveWeatherIcon = responseBody.list[i].weather[0].icon
                            //create the url with the icon code
//                            val url = "https://openweathermap.org/img/wn/$dayFiveWeatherIcon@2x.png"
                            val imageView = findViewById<ImageView>(R.id.day_five_weather_icon)
//                            Picasso.get().load(url).into(imageView)

                            setIconImage(dayFiveWeatherIcon, imageView, false)

                            //load the temp
                            val dayFiveTemp = responseBody.list[i].main.temp.toString()
                            binding.dayFiveTemp.text = dayFiveTemp
                            lastIndex = i + 1
                            break
                        }
                    }
                    //day six
                    for (i in lastIndex..<responseBody.list.size) {
                        if (responseBody.list[i].dt_txt.contains(noon)) {
                            //get the icon code
                            val daySixWeatherIcon = responseBody.list[i].weather[0].icon
                            //create the url with the icon code
//                            val url = "https://openweathermap.org/img/wn/$daySixWeatherIcon@2x.png"
                            val imageView = findViewById<ImageView>(R.id.day_six_weather_icon)
//                            Picasso.get().load(url).into(imageView)

                            setIconImage(daySixWeatherIcon, imageView, false)

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
                    fetchCurrentWeatherData(query, currentSettings.unitOfMeasure)
                    fetchFiveDayForecastData(query, currentSettings.unitOfMeasure)
                    currentSettings.cityName = query
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }
    private fun fetchCurrentWeatherData(cityName:String, unit: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(CurrentWeatherApi::class.java)

        val response = retrofit.getCurrentWeatherData(cityName, "91a0c7771716b8912caf89842ed9b946", unit)
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
                    //set custom icon code
                    //val url = "https://openweathermap.org/img/wn/$iconCode@2x.png"
                    //val imageView = findViewById<ImageView>(R.id.day_one_weather_icon)
                    val imageView = findViewById<ImageView>(R.id.day_one_weather_icon)
                    setIconImage(iconCode, imageView, true)
                    //set the background according to weather
                    setBackground(iconCode)

                    //display the icon
                    //Picasso.get().load(url).into(imageView)

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

    private fun setIconImage(iconCode:String, currentWeatherImage:ImageView, isCurrentWeather:Boolean) {
        //clouds 02 03 04
        if (iconCode.contains("02") || iconCode.contains("03") || iconCode.contains("04")){
            currentWeatherImage.setBackgroundResource(R.drawable.clouds)
        }
        //clearsky 01
        else if (iconCode.contains("01d")){
            currentWeatherImage.setBackgroundResource(R.drawable.sunny)
        }
        //clearsky night 01n
        else if (iconCode.contains("01n") && isCurrentWeather){
            currentWeatherImage.setBackgroundResource(R.drawable.moon)
        }
        //Rain 09 10 11
        else if (iconCode.contains("09") || iconCode.contains("10") || iconCode.contains("11")){
            currentWeatherImage.setBackgroundResource(R.drawable.rain)
        }
        //snow 13
        else if (iconCode.contains("13")){
            currentWeatherImage.setBackgroundResource(R.drawable.snow)
        }
        //mist 50
        else{
            currentWeatherImage.setBackgroundResource(R.drawable.mist)
        }
    }
}
