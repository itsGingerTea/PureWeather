package com.example.pureweather.data.models

import com.google.gson.annotations.SerializedName

data class WeatherDesc (
    @SerializedName("description")
    val desc: String? = null,
    val code: Int? = null,
    val icon: String? = null,
)
