package com.example.pureweather.di.tabs

import android.view.LayoutInflater
import com.example.pureweather.ui.MainFragment
import dagger.BindsInstance
import dagger.Subcomponent

@TabsScope
@Subcomponent
interface TabsComponent {

    fun inject(fragment: MainFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun bindInflater(inflater: LayoutInflater): Builder

        fun build(): TabsComponent
    }
}