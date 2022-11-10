package com.example.pureweather.data.network

import com.example.pureweather.data.models.Day
import com.example.pureweather.data.models.Week
import com.example.pureweather.utils.ApiState
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2.0/current")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?
    ): Response<Day>

    @GET("v2.0/forecast/daily")
    suspend fun getWeekWeather(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("days") days: Int
    ): Response<Week>

    @GET("v2.0/current")
    suspend fun getCurrentWeatherByCity(
        @Query("city") city: String?
    ): Response<Day>

    @GET("v2.0/forecast/daily")
    suspend fun getWeekWeatherByCity(
        @Query("days") days: Int,
        @Query("city") city: String?
    ): Response<Week>
}