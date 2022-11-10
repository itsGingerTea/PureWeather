package com.example.pureweather.di.main

import android.view.LayoutInflater
import com.example.pureweather.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@MainScope
@Subcomponent
interface MainComponent {

    fun inject(activity: MainActivity)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun bindInflater(inflater: LayoutInflater): Builder

        fun build(): MainComponent
    }
}