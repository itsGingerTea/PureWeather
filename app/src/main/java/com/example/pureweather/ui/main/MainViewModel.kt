package com.example.pureweather.ui.main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pureweather.data.models.Day
import com.example.pureweather.data.models.Week
import com.example.pureweather.domain.WeatherInteractor
import com.example.pureweather.utils.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: WeatherInteractor): ViewModel() {

    private val _location: MutableLiveData<Location> = MutableLiveData()
    val location: LiveData<Location> get() = _location

    val selectingCity: SingleLiveEvent<String> = SingleLiveEvent()
    private val _cities: MutableLiveData<Location> = MutableLiveData()
    val cities: LiveData<Location> get() = _cities
    private val _dayState: MutableLiveData<ApiState<*>> = MutableLiveData()
    val dayState: LiveData<ApiState<*>> get() = _dayState
    private val _weekState: MutableLiveData<ApiState<*>> = MutableLiveData()
    val weekState: LiveData<ApiState<*>> get() = _weekState


    fun onLocationExtracted(location: Location) {
        _location.postValue(location)
        interactor.saveLocation(location)
        loadCurrentWeather(location)
        loadWeeklyWeather(location)
    }

    fun onCitySelected(city: String) {
        selectingCity.postValue(city)
    }

    fun onCityExtracted(city: String) {
        loadCurrentWeatherByCity(city)
        loadWeeklyWeatherByCity(city)
    }

    private fun loadWeeklyWeather(location: Location) {
        _weekState.postValue(Loading)
        viewModelScope.launch {
            val result = interactor.getWeeklyWeather(location.latitude, location.longitude, DAYS)
            when (result) {
                is Success<*> -> _weekState.postValue(Success(result.data as Week))
                is Error -> _weekState.postValue(Error(result.e, result.message))
                else -> {}
            }
        }
    }

    private fun loadCurrentWeather(location: Location) {
        _dayState.postValue(Loading)
        viewModelScope.launch {
            val result = interactor.getCurrentWeather(location.latitude, location.longitude)
            when (result) {
                is Success<*> -> _dayState.postValue(Success(result.data as Day))
                is Error -> _dayState.postValue(Error(result.e, result.message))
                else -> {}
            }
        }
    }

    private fun loadWeeklyWeatherByCity(city: String) {
        _weekState.postValue(Loading)
        viewModelScope.launch {
            val result = interactor.getWeeklyWeatherByCity(DAYS, city)
            when (result) {
                is Success<*> -> _weekState.postValue(Success(result.data as Week))
                is Error -> _weekState.postValue(Error(result.e, result.message))
                else -> {}
            }
        }
    }

    private fun loadCurrentWeatherByCity(city: String) {
        _dayState.postValue(Loading)
        viewModelScope.launch {
            val result = interactor.getCurrentWeatherByCity(city)
            when (result) {
                is Success<*> -> _dayState.postValue(Success(result.data as Day))
                is Error -> _dayState.postValue(Error(result.e, result.message))
                else -> {}
            }
        }
    }

    companion object {
        private const val DAYS = 7
    }
}