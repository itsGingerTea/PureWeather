package com.example.pureweather.di

import android.content.Context
import com.example.pureweather.di.day.DayComponent
import com.example.pureweather.di.first.FirstComponent
import com.example.pureweather.di.main.MainComponent
import com.example.pureweather.di.search.SearchComponent
import com.example.pureweather.di.tabs.TabsComponent
import com.example.pureweather.di.week.WeekComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun mainComponentBuilder(): MainComponent.Builder
    fun firstComponentBuilder(): FirstComponent.Builder
    fun tabsComponentBuilder(): TabsComponent.Builder
    fun dayComponentBuilder(): DayComponent.Builder
    fun weekComponentBuilder(): WeekComponent.Builder
    fun searchComponentBuilder(): SearchComponent.Builder

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindsApplication(context: Context): Builder

        fun build(): AppComponent
    }
}