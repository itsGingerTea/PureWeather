package com.example.pureweather.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pureweather.di.main.MainScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class SearchViewModelFactory @AssistedInject constructor() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == SearchViewModel::class.java)
        return SearchViewModel() as T
    }

    @AssistedFactory
    @MainScope
    interface Factory {
        fun create(): SearchViewModelFactory
    }
}