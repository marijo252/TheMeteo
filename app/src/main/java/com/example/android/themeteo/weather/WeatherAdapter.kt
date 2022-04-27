package com.example.android.themeteo.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.android.themeteo.R
import com.example.android.themeteo.databinding.DailyMeteoBinding
import com.example.android.themeteo.databinding.SunriseSunsetBinding
import com.example.android.themeteo.databinding.TodaysDataBinding
import java.util.*

class WeatherAdapter : RecyclerView.Adapter<WeatherViewHolder>() {

    var items = listOf<WeatherRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        when(holder){
            is WeatherViewHolder.DailyMeteo -> holder.bind(items[position] as WeatherRecyclerViewItem.DailyMeteos)
            is WeatherViewHolder.TodaySunriseSunset -> holder.bind(items[position] as WeatherRecyclerViewItem.TodaySunriseSunset)
            is WeatherViewHolder.TodayData -> holder.bind(items[position] as WeatherRecyclerViewItem.TodayData)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return when(viewType){
            R.layout.daily_meteo -> WeatherViewHolder.DailyMeteo(
                DailyMeteoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.sunrise_sunset -> WeatherViewHolder.TodaySunriseSunset(
                SunriseSunsetBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.todays_data -> WeatherViewHolder.TodayData(
                TodaysDataBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is WeatherRecyclerViewItem.DailyMeteos -> R.layout.daily_meteo
            is WeatherRecyclerViewItem.TodaySunriseSunset -> R.layout.sunrise_sunset
            is WeatherRecyclerViewItem.TodayData -> R.layout.todays_data
        }
    }
}

sealed class WeatherViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class DailyMeteo(private val binding: DailyMeteoBinding): WeatherViewHolder(binding){
        fun bind(dailyMeteos: WeatherRecyclerViewItem.DailyMeteos){
            val calendar = Calendar.getInstance()
            calendar.time = dailyMeteos.dailyData.minByOrNull { it.date }!!.date
            val days = arrayListOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")

            for (day in dailyMeteos.dailyData){
                when(day.date.day){
                    calendar.time.day -> {
                        binding.lowestTempToday.text = String.format(binding.lowestTempToday.context.getString(R.string.temperature),day.minTemperature)
                        binding.maxTempToday.text = String.format(binding.maxTempToday.context.getString(R.string.temperature),day.maxTemperature)
                    }
                    (calendar.time.day + 1) -> {
                        binding.lowestTempToday1.text = String.format(binding.lowestTempToday1.context.getString(R.string.temperature),day.minTemperature)
                        binding.maxTempToday1.text = String.format(binding.maxTempToday1.context.getString(R.string.temperature),day.maxTemperature)
                        binding.today1.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 2) -> {
                        binding.lowestTempToday2.text = String.format(binding.lowestTempToday2.context.getString(R.string.temperature),day.minTemperature)
                        binding.maxTempToday2.text = String.format(binding.maxTempToday2.context.getString(R.string.temperature),day.maxTemperature)
                        binding.today2.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 3) -> {
                        binding.lowestTempToday3.text = String.format(binding.lowestTempToday3.context.getString(R.string.temperature),day.minTemperature)
                        binding.maxTempToday3.text = String.format(binding.maxTempToday3.context.getString(R.string.temperature),day.maxTemperature)
                        binding.today3.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 4) -> {
                        binding.lowestTempToday4.text = String.format(binding.lowestTempToday4.context.getString(R.string.temperature),day.minTemperature)
                        binding.maxTempToday4.text = String.format(binding.maxTempToday4.context.getString(R.string.temperature),day.maxTemperature)
                        binding.today4.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 5) -> {
                        binding.lowestTempToday5.text = String.format(binding.lowestTempToday5.context.getString(R.string.temperature),day.minTemperature)
                        binding.maxTempToday5.text = String.format(binding.maxTempToday5.context.getString(R.string.temperature),day.maxTemperature)
                        binding.today5.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 6) -> {
                        binding.lowestTempToday6.text = String.format(binding.lowestTempToday6.context.getString(R.string.temperature),day.minTemperature)
                        binding.maxTempToday6.text = String.format(binding.maxTempToday6.context.getString(R.string.temperature),day.maxTemperature)
                        binding.today6.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 7) -> {
                        binding.lowestTempToday7.text = String.format(binding.lowestTempToday7.context.getString(R.string.temperature),day.minTemperature)
                        binding.maxTempToday7.text = String.format(binding.maxTempToday7.context.getString(R.string.temperature),day.maxTemperature)
                        binding.today7.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                }
            }
        }
    }

    class TodaySunriseSunset(private val binding: SunriseSunsetBinding): WeatherViewHolder(binding){
        fun bind(todaySunriseSunset: WeatherRecyclerViewItem.TodaySunriseSunset){
            binding.sunriseTime.text = todaySunriseSunset.sunrise
            binding.sunsetTime.text = todaySunriseSunset.sunset
        }
    }

    class TodayData(private val binding: TodaysDataBinding): WeatherViewHolder(binding){
        fun bind(todayData: WeatherRecyclerViewItem.TodayData){
            binding.uvIndexValue.text = todayData.uvi.toString()
            binding.feelsLikeValue.text = String.format(binding.feelsLikeValue.context.getString(R.string.temperature),todayData.feelsLike)
            binding.humidityValue.text = String.format(binding.humidityValue.context.getString(R.string.humidity_value),todayData.humidity)
            binding.windValue.text = String.format(binding.windValue.context.getString(R.string.wind_value),todayData.windSpeed)
            binding.visibilityValue.text = String.format(binding.visibilityValue.context.getString(R.string.visibility_value),todayData.visibility)
            binding.pressureValue.text = String.format(binding.pressureValue.context.getString(R.string.pressure_value),todayData.pressure)
        }
    }
}