package com.example.android.themeteo.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.android.themeteo.R
import com.example.android.themeteo.databinding.DailyMeteoBinding
import com.example.android.themeteo.databinding.SunriseSunsetBinding
import com.example.android.themeteo.databinding.TodaysDataBinding
import com.example.android.themeteo.domains.DailyWeather
import com.example.android.themeteo.domains.Weather
import java.util.*

class WeatherAdapter : RecyclerView.Adapter<WeatherViewHolder>() {

    var items = listOf<Weather>()


    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        when(holder){
            is WeatherViewHolder.DailyMeteo -> holder.bind(items[position])
            is WeatherViewHolder.todaySunriseSunset -> holder.bind(items[position])
            is WeatherViewHolder.todaysData -> holder.bind(items[position])
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
            R.layout.sunrise_sunset -> WeatherViewHolder.todaySunriseSunset(
                SunriseSunsetBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.todays_data -> WeatherViewHolder.todaysData(
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
}

sealed class WeatherViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class DailyMeteo(private val binding: DailyMeteoBinding): WeatherViewHolder(binding){
        fun bind(weather: Weather){
            val calendar = Calendar.getInstance()
            calendar.time = weather.daily.first().date
            val days = arrayListOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")

            for (day in weather.daily){
                when(day.date.day){
                    calendar.time.day -> {
                        binding.lowestTempToday.text = day.temperature.min.toString()
                        binding.maxTempToday.text = day.temperature.max.toString()
                    }
                    (calendar.time.day + 1) -> {
                        binding.lowestTempToday1.text = day.temperature.min.toString()
                        binding.maxTempToday1.text = day.temperature.max.toString()
                        binding.today1.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 2) -> {
                        binding.lowestTempToday2.text = day.temperature.min.toString()
                        binding.maxTempToday2.text = day.temperature.max.toString()
                        binding.today2.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 3) -> {
                        binding.lowestTempToday3.text = day.temperature.min.toString()
                        binding.maxTempToday3.text = day.temperature.max.toString()
                        binding.today3.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 4) -> {
                        binding.lowestTempToday4.text = day.temperature.min.toString()
                        binding.maxTempToday4.text = day.temperature.max.toString()
                        binding.today4.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 5) -> {
                        binding.lowestTempToday5.text = day.temperature.min.toString()
                        binding.maxTempToday5.text = day.temperature.max.toString()
                        binding.today5.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 6) -> {
                        binding.lowestTempToday6.text = day.temperature.min.toString()
                        binding.maxTempToday1.text = day.temperature.max.toString()
                        binding.today6.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                    (calendar.time.day + 7) -> {
                        binding.lowestTempToday7.text = day.temperature.min.toString()
                        binding.maxTempToday7.text = day.temperature.max.toString()
                        binding.today7.text = days[calendar.get(Calendar.DAY_OF_WEEK)]
                    }
                }
            }
        }
    }

    class todaySunriseSunset(private val binding: SunriseSunsetBinding): WeatherViewHolder(binding){
        fun bind(weather: Weather){
            binding.sunriseTime.text = weather.current.sunrise.toString()
            binding.sunsetTime.text = weather.current.sunset.toString()
        }
    }

    class todaysData(private val binding: TodaysDataBinding): WeatherViewHolder(binding){
        fun bind(weather: Weather){
            binding.uvIndexValue.text = weather.current.uvi.toString()
            binding.feelsLikeValue.text = weather.current.feelsLike.toString()
            binding.humidityValue.text = weather.current.humidity.toString()
            binding.windValue.text = weather.current.windSpeed.toString()
            binding.visibilityValue.text = weather.current.visibility.toString()
            binding.pressureValue.text = weather.current.pressure.toString()
        }
    }
}