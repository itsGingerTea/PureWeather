package com.example.pureweather.data.models

import com.google.gson.annotations.SerializedName

data class WeekData(
    val clouds: Int?,
    @SerializedName("high_temp")
    val highTemp: Double? = null,
    @SerializedName("low_temp")
    val lowTemp: Double? = null,
    val pop: Int?,
    val pres: Double?,
    val rh: Int?,
    val temp: Double?,
    val ts: Long?,
    val uv: Double?,
    val weather: WeatherDesc?,
    @SerializedName("wind_cdir")
    val windCdir: String?,
    @SerializedName("wind_cdir_full")
    val winCdirFull: String?,
    @SerializedName("wind_spd")
    val windSpd: Double?
)
