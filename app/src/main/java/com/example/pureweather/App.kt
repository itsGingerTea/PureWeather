package com.example.pureweather

import android.app.Application
import com.example.pureweather.di.AppComponent
import com.example.pureweather.di.DaggerAppComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().bindsApplication(this).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}