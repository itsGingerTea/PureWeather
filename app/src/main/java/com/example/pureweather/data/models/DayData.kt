package com.example.pureweather.data.models

import com.google.gson.annotations.SerializedName

data class DayData (
    @SerializedName("city_name")
    val cityName: String? = null,
    val clouds: Int?,
    @SerializedName("country_code")
    val countryCode: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val pres: Double?,
    val rh: Int?,
    val temp: Double?,
    val timezone: String? = null,
    val ts: Long?,
    val pop: Int?,
    val uv: Double?,
    val weather: WeatherDesc?,
    @SerializedName("wind_cdir")
    val windCdir: String?,
    @SerializedName("wind_cdir_full")
    val winCdirFull: String?,
    @SerializedName("wind_spd")
    val windSpd: Double?
)
