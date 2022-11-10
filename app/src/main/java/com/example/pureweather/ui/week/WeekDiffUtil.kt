package com.example.pureweather.ui.week

import androidx.recyclerview.widget.DiffUtil
import com.example.pureweather.data.models.Week
import com.example.pureweather.data.models.WeekData

object WeekDiffUtil : DiffUtil.ItemCallback<WeekData>() {
    override fun areItemsTheSame(oldItem: WeekData, newItem: WeekData): Boolean {
        return oldItem.ts == newItem.ts
    }

    override fun areContentsTheSame(oldItem: WeekData, newItem: WeekData): Boolean {
        return oldItem == newItem
    }
}
