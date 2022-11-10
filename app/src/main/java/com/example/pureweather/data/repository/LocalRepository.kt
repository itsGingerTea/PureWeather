package com.example.pureweather.data.repository

interface LocalRepository {
    fun saveLat(lat:Double)
    fun saveLon(lon:Double)
    fun isLocationSaved(): Boolean
    fun getLat(): Double?
    fun getLon(): Double?
    fun clear()
}