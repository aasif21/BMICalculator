package com.example.weatherapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private  val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchWeatherData("Pune")
        searchCity()
    }

    private fun searchCity() {
        val searchview=binding.searchView
        searchview.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    private fun fetchWeatherData(cityName:String) {
        val retrofit=Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.openweathermap.org/data/2.5/").build()
            .create(ApiInterface::class.java)
        val response=retrofit.getWeatherData(cityName, "130578c6fd1a9d281663bb0adf435cd4","metric")
        response.enqueue(object:Callback<WeatherApp> {
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                val temperature = responseBody.main.temp.toString()
                    val humidity=responseBody.main.humidity
                    val windspeed=responseBody.wind.speed
                    val sunRise=responseBody.sys.sunrise.toLong()
                    val sunSet=responseBody.sys.sunset.toLong()
                    val seaLevel=responseBody.main.pressure
                    val condition=responseBody.weather.firstOrNull()?.main?:"unknown"
                    val maxTemp=responseBody.main.temp_max
                    val minTemp=responseBody.main.temp_min
                    binding.temp.text="$temperature ˚C"
                    binding.weather.text=condition
                    binding.maxtemp.text= "Max Temp: $maxTemp ˚C"
                    binding.mintemp.text= "Min Temp: $minTemp ˚C"
                    binding.humidity.text="$humidity %"
                    binding.windspeed.text="$windspeed m/s"
                    binding.sunrise.text="${time(sunRise)}"
                    binding.sunset.text="${time(sunSet)}"
                    binding.sea.text="$seaLevel hPa"
                    binding.conditions.text= condition

                    binding.day.text=dayName(System.currentTimeMillis())
                    binding.date.text=date()


                    Log.d("TAG", "onResponse:$temperature")
                    binding.cityname.text = cityName

                    changeImgAccordingly(condition)
            }
        }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun changeImgAccordingly(conditions:String) {
        when(conditions) {
            "Clear Sky", "Sunny", "Clear" -> {
                binding.root.setBackgroundResource(R.drawable.img_4)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }

            "HAZE", "Cloudy", "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy" -> {
                binding.root.setBackgroundResource(R.drawable.img_1)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }

            "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Overcast", "Heavy Rain" -> {
                binding.root.setBackgroundResource(R.drawable.img_2)
                binding.lottieAnimationView.setAnimation(R.raw.rain)

            }

            "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard", "Snowfall" -> {
                binding.root.setBackgroundResource(R.drawable.img_1)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            } else -> {
                binding.root.setBackgroundResource(R.drawable.img_4)
            binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
        }
        binding.lottieAnimationView.playAnimation()
    }

    private fun date(): String {
        val sdf= SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(Date())


    }private fun time(timestamp: Long): String {
        val sdf= SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp*1000))


    }

    fun dayName(timestamp:Long) :String{
        val sdf= SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date())

    }

}