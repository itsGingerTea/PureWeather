package com.example.pureweather.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pureweather.di.main.MainScope
import com.example.pureweather.domain.WeatherInteractor
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MainViewModelFactory @AssistedInject constructor(private val interactor: WeatherInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == MainViewModel::class.java)
        return MainViewModel(interactor) as T
    }

    @AssistedFactory
    @MainScope
    interface Factory {
        fun create(): MainViewModelFactory
    }
}