package com.example.pureweather.di.search

import android.view.LayoutInflater
import com.example.pureweather.ui.search.SearchFragment
import dagger.BindsInstance
import dagger.Subcomponent

@SearchScope
@Subcomponent
interface SearchComponent {

    fun inject(fragment: SearchFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun bindInflater(inflater: LayoutInflater): Builder

        fun build(): SearchComponent
    }
}