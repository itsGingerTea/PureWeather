package com.example.pureweather.di.week

import android.view.LayoutInflater
import com.example.pureweather.ui.week.WeekFragment
import dagger.BindsInstance
import dagger.Subcomponent

@WeekScope
@Subcomponent
interface WeekComponent {

    fun inject(fragment: WeekFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun bindInflater(inflater: LayoutInflater): Builder

        fun build(): WeekComponent
    }
}