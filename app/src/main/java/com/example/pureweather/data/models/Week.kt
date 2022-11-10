package com.example.pureweather.data.models

import com.google.gson.annotations.SerializedName

data class Week(
    @SerializedName("city_name")
    val cityName: String? = null,
    val countryCode: String? = null,
    val data: List<WeekData>? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val timezone: String? = null,
)
