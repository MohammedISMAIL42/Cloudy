package com.example.cloudy.repository

import com.example.cloudy.api.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getWeather(city: String, apiKey: String) = apiService.getWeather(city, apiKey)
}