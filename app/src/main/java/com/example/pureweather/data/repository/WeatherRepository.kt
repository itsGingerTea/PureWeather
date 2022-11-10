package com.example.pureweather.data.repository

import com.example.pureweather.data.models.Day
import com.example.pureweather.data.models.Week
import com.example.pureweather.utils.ApiState

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double?, lon: Double?): ApiState<Day?>
    suspend fun getWeekWeather(lat: Double?, lon: Double?, days: Int): ApiState<Week?>
    suspend fun getCurrentWeatherByCity(city: String?): ApiState<Day?>
    suspend fun getWeekWeatherByCity(days: Int, city: String?): ApiState<Week?>
}