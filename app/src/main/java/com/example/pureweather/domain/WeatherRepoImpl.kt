package com.example.pureweather.domain

import com.example.pureweather.data.models.Day
import com.example.pureweather.data.models.Week
import com.example.pureweather.data.repository.WeatherRepository
import com.example.pureweather.data.network.ApiService
import com.example.pureweather.utils.ApiState
import com.example.pureweather.utils.Success
import com.example.pureweather.utils.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(private val api: ApiService): WeatherRepository {
    override suspend fun getCurrentWeather(lat:Double?,lon: Double?): ApiState<Day?> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getCurrentWeather(lat, lon)
                if (response.isSuccessful) {
                    val body = response.body()
                    Success(body)
                } else Error(java.lang.Exception(), response.message())
            } catch (e: Exception) {
                Error(e, e.message)
            }
        }

    override suspend fun getWeekWeather(lat:Double?, lon: Double?, days: Int): ApiState<Week?> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getWeekWeather(lat, lon, days)
                if (response.isSuccessful) {
                    val body = response.body()
                    Success(body)
                } else Error(java.lang.Exception(), response.message())
            } catch (e: Exception) {
                Error(e, e.message)
            }
        }

    override suspend fun getCurrentWeatherByCity(city: String?): ApiState<Day?> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getCurrentWeatherByCity(city)
                if (response.isSuccessful) {
                    val body = response.body()
                    Success(body)
                } else Error(java.lang.Exception(), response.message())
            } catch (e: Exception) {
                Error(e, e.message)
            }
        }

    override suspend fun getWeekWeatherByCity(days: Int, city: String?): ApiState<Week?> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getWeekWeatherByCity(days, city)
                if (response.isSuccessful) {
                    val body = response.body()
                    Success(body)
                } else Error(java.lang.Exception(), response.message())
            } catch (e: Exception) {
                Error(e, e.message)
            }
        }

    companion object {
        private const val DAYS = 7
    }
}