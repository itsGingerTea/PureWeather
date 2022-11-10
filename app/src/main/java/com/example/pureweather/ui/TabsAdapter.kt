package com.example.pureweather.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pureweather.ui.day.DayFragment
import com.example.pureweather.ui.week.WeekFragment

class TabsAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragmentWeek = WeekFragment()
        val fragmentDay = DayFragment()
        return if (position == 0) {
            fragmentDay
        } else {
            fragmentWeek
        }
    }
}