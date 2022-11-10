package com.example.pureweather.ui.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pureweather.R
import com.example.pureweather.data.models.WeekData
import com.example.pureweather.databinding.RvWeekItemBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class WeekAdapter : ListAdapter<WeekData, WeekAdapter.WeekViewHolder>(WeekDiffUtil) {

    inner class WeekViewHolder(private val itemBinding: RvWeekItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dayOfWeek: WeekData) {
            with(itemBinding) {
                val ts = dayOfWeek.ts
                val calendar = Calendar.getInstance()
                if (ts != null) {
                    calendar.timeInMillis = ts.toLong() * 1000
                }
                val dayOfWeekCurrent =
                    SimpleDateFormat("EEEE", Locale.ENGLISH).format(Calendar.getInstance().time)
                val dayCurrent =
                    SimpleDateFormat("dd", Locale.ENGLISH).format(Calendar.getInstance().time)
                val dayOfWeek1 = SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.timeInMillis)
                val day = SimpleDateFormat("dd", Locale.ENGLISH).format(calendar.timeInMillis)
                Picasso.get()
                    .load(root.context.getString(R.string.load_image, dayOfWeek.weather?.icon))
                    .into(itemBinding.ivWeekDay)

                if (dayCurrent == day && dayOfWeekCurrent == dayOfWeek1) {
                    tvWeekDay.text = root.context.getString(R.string.today)
                } else {
                    tvWeekDay.text = root.context.getString(R.string.day_week, day, dayOfWeek1)
                }
                tvWeekDayDesc.text = dayOfWeek.weather?.desc
                tvWeekTemps.text =
                    root.context.getString(R.string.temps, dayOfWeek.lowTemp, dayOfWeek.highTemp)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        return WeekViewHolder(
            itemBinding = RvWeekItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}