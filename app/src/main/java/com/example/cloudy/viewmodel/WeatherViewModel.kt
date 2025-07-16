package com.example.cloudy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cloudy.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudy.model.Weather
import com.example.cloudy.utils.Constants
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(private val repository: WeatherRepository): ViewModel()  {
    private val _resp= MutableLiveData<Weather>()
    val weatherResp: LiveData<Weather>
        get() = _resp

    init {
        getWeather(
            city = "bangalore"
        )
    }
    fun getWeather(city:String) = viewModelScope.launch {
        //val city = "Bangalore"
        val apiKey = Constants.API_KEY
        val response = repository.getWeather(city, apiKey)

        if (response.isSuccessful && response.body() != null) {
            Log.d("API_SUCCESS", "Weather: ${response.body()}")
            _resp.postValue(response.body())
        } else {
            Log.e("API_ERROR", "Error: ${response.code()} - ${response.message()}")
        }


    }
}