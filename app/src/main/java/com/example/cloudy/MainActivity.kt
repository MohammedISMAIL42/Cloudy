package com.example.cloudy


import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.cloudy.databinding.ActivityMainBinding
import com.example.cloudy.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotBlank()) {
                        viewModel.getWeather(it.trim())
                    }
                }
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })



        viewModel.weatherResp.observe(this) { weather ->
            weather?.let {

                binding.temperature.text = "${weather.main.temp}°C"
                binding.cityName.text = it.name
                binding.maxTemp.text = "Max ${it.main.temp_max} °C"
                binding.minTemp.text = "Min ${it.main.temp_min} °C"
                binding.humidity.text = "${it.main.humidity} %"


//                // 🌬️ Wind speed
               binding.WindSpeed.text = "${it.wind.speed} m/s"
                // 📅 Date and Day
                binding.date.text = formatDate(System.currentTimeMillis())
                binding.day.text = formatDay(System.currentTimeMillis())
                // 🌊 Sea level (approximated from pressure)
                binding.sea.text = "${it.main.pressure} hPa"



            }
        }
    }
    private fun formatTime(timestamp: Int): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        // return sdf.format(Date(timestamp * 1000))
        return TODO("Provide the return value")
    }

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private fun formatDay(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }


}

