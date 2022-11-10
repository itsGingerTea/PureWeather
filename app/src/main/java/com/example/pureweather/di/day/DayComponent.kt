package com.example.pureweather.di.day

import android.view.LayoutInflater
import com.example.pureweather.ui.day.DayFragment
import dagger.BindsInstance
import dagger.Subcomponent

@DayScope
@Subcomponent
interface DayComponent {

    fun inject(fragment: DayFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun bindInflater(inflater: LayoutInflater): Builder

        fun build(): DayComponent
    }
}