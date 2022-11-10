package com.example.pureweather.di.first

import android.view.LayoutInflater
import com.example.pureweather.ui.FirstFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FirstScope
@Subcomponent
interface FirstComponent {

    fun inject(fragment: FirstFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun bindInflater(inflater: LayoutInflater): Builder

        fun build(): FirstComponent
    }
}