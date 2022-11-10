package com.example.pureweather.domain

import android.location.Location
import com.example.pureweather.data.models.Day
import com.example.pureweather.data.models.Week
import com.example.pureweather.data.repository.LocalRepository
import com.example.pureweather.data.repository.WeatherRepository
import com.example.pureweather.utils.ApiState
import javax.inject.Inject

class WeatherInteractor @Inject constructor(
    private val localRepo: LocalRepository,
    private val weatherRepo: WeatherRepository
) {

    suspend fun getCurrentWeather(lat: Double?, lon: Double?): ApiState<Day?> {
        return weatherRepo.getCurrentWeather(lat, lon)
    }

    suspend fun getWeeklyWeather(lat: Double?, lon: Double?, days: Int): ApiState<Week?> {
        return weatherRepo.getWeekWeather(lat, lon, days)
    }

    suspend fun getCurrentWeatherByCity(city: String?): ApiState<Day?> {
        return weatherRepo.getCurrentWeatherByCity(city)
    }

    suspend fun getWeeklyWeatherByCity(days: Int,city: String?): ApiState<Week?> {
        return weatherRepo.getWeekWeatherByCity(days, city)
    }

    fun saveLocation(location: Location) {
        localRepo.saveLat(location.latitude)
        localRepo.saveLon(location.longitude)
    }
}