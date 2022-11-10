package com.example.pureweather.domain

import android.content.SharedPreferences
import com.example.pureweather.data.repository.LocalRepository
import javax.inject.Inject

class LocalRepoImpl @Inject constructor(private val sharedPref: SharedPreferences) :
    LocalRepository {

    override fun saveLat(lat: Double) {
        sharedPref.edit().putString(LAT, lat.toString()).apply()
    }

    override fun saveLon(lon: Double) {
        sharedPref.edit().putString(LON, lon.toString()).apply()
    }

    override fun isLocationSaved(): Boolean {
        return sharedPref.contains(LAT)
    }

    override fun getLat(): Double? {
        return sharedPref.getString(LAT, null)?.toDouble()
    }

    override fun getLon(): Double? {
        return sharedPref.getString(LON, null)?.toDouble()
    }

    override fun clear() {
        sharedPref.edit()
            .clear()
            .apply()
    }

    companion object {
        const val PREFS = "prefs"
        const val LAT = "lat"
        const val LON = "lon"
    }
}