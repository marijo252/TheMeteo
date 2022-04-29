package com.example.android.themeteo.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.android.themeteo.R
import com.example.android.themeteo.databinding.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class WeatherAdapter(
    private val onAirQualitySeeMoreClicked: (WeatherRecyclerViewItem.AirQuality) -> Unit,
    private val onDailyAlertsSeeMoreClicked: (WeatherRecyclerViewItem.DailyAlerts) -> Unit,
) : RecyclerView.Adapter<WeatherViewHolder>() {

    var items = listOf<WeatherRecyclerViewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        when(holder){
            is WeatherViewHolder.DailyMeteo -> holder.bind(items[position]
                    as WeatherRecyclerViewItem.DailyMeteos)
            is WeatherViewHolder.TodaySunriseSunset -> holder.bind(items[position]
                    as WeatherRecyclerViewItem.TodaySunriseSunset)
            is WeatherViewHolder.TodayData -> holder.bind(items[position]
                    as WeatherRecyclerViewItem.TodayData)
            is WeatherViewHolder.AirQuality -> holder.bind(items[position]
                    as WeatherRecyclerViewItem.AirQuality)
            is WeatherViewHolder.DailyAlerts -> holder.bind(items[position]
                    as WeatherRecyclerViewItem.DailyAlerts)
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
            R.layout.air_quality -> WeatherViewHolder.AirQuality(
                AirQualityBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onAirQualitySeeMoreClicked
            )
            R.layout.daily_alerts -> WeatherViewHolder.DailyAlerts(
                DailyAlertsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onDailyAlertsSeeMoreClicked
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
            is WeatherRecyclerViewItem.AirQuality -> R.layout.air_quality
            is WeatherRecyclerViewItem.DailyAlerts -> R.layout.daily_alerts
        }
    }
}

sealed class WeatherViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class DailyMeteo(private val binding: DailyMeteoBinding): WeatherViewHolder(binding){
        fun bind(dailyMeteos: WeatherRecyclerViewItem.DailyMeteos){
            val calendar = Calendar.getInstance()
            val startDate = dailyMeteos.dailyData.minByOrNull { it.date }!!.date
            val days = arrayListOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday")


            for (day in dailyMeteos.dailyData){
                when(day.date){
                    startDate -> {
                        binding.lowestTempToday.text = String.format(
                            binding.lowestTempToday.context.getString(R.string.temperature),
                            day.minTemperature)
                        binding.maxTempToday.text = String.format(
                            binding.maxTempToday.context.getString(R.string.temperature),
                            day.maxTemperature)
                        Glide.with(binding.imageTempToday.context)
                            .load(day.url.toUri().buildUpon().scheme("https").build())
                            .into(binding.imageTempToday)
                    }
                    Date(startDate.time + 86400000) -> {
                        binding.lowestTempToday1.text = String.format(
                            binding.lowestTempToday1.context.getString(R.string.temperature),
                            day.minTemperature)
                        binding.maxTempToday1.text = String.format(
                            binding.maxTempToday1.context.getString(R.string.temperature),
                            day.maxTemperature)
                        binding.today1.text = days[calendar.get(Calendar.DAY_OF_WEEK)-1]
                        Glide.with(binding.imageTempToday1.context)
                            .load(day.url.toUri().buildUpon().scheme("https").build())
                            .into(binding.imageTempToday1)
                    }
                    Date(startDate.time + 86400000*2) -> {
                        binding.lowestTempToday2.text = String.format(
                            binding.lowestTempToday2.context.getString(R.string.temperature),
                            day.minTemperature)
                        binding.maxTempToday2.text = String.format(
                            binding.maxTempToday2.context.getString(R.string.temperature),
                            day.maxTemperature)
                        binding.today2.text = days[calendar.get(Calendar.DAY_OF_WEEK)-1]
                        Glide.with(binding.imageTempToday2.context)
                            .load(day.url.toUri().buildUpon().scheme("https").build())
                            .into(binding.imageTempToday2)
                    }
                    Date(startDate.time + 86400000*3) -> {
                        binding.lowestTempToday3.text = String.format(
                            binding.lowestTempToday3.context.getString(R.string.temperature),
                            day.minTemperature)
                        binding.maxTempToday3.text = String.format(
                            binding.maxTempToday3.context.getString(R.string.temperature),
                            day.maxTemperature)
                        binding.today3.text = days[calendar.get(Calendar.DAY_OF_WEEK)-1]
                        Glide.with(binding.imageTempToday3.context)
                            .load(day.url.toUri().buildUpon().scheme("https").build())
                            .into(binding.imageTempToday3)
                    }
                    Date(startDate.time + 86400000*4) -> {
                        binding.lowestTempToday4.text = String.format(
                            binding.lowestTempToday4.context.getString(R.string.temperature),
                            day.minTemperature)
                        binding.maxTempToday4.text = String.format(
                            binding.maxTempToday4.context.getString(R.string.temperature),
                            day.maxTemperature)
                        binding.today4.text = days[calendar.get(Calendar.DAY_OF_WEEK)-1]
                        Glide.with(binding.imageTempToday4.context)
                            .load(day.url.toUri().buildUpon().scheme("https").build())
                            .into(binding.imageTempToday4)
                    }
                    Date(startDate.time + 86400000*5) -> {
                        binding.lowestTempToday5.text = String.format(
                            binding.lowestTempToday5.context.getString(R.string.temperature),
                            day.minTemperature)
                        binding.maxTempToday5.text = String.format(
                            binding.maxTempToday5.context.getString(R.string.temperature),
                            day.maxTemperature)
                        binding.today5.text = days[calendar.get(Calendar.DAY_OF_WEEK)-1]
                        Glide.with(binding.imageTempToday5.context)
                            .load(day.url.toUri().buildUpon().scheme("https").build())
                            .into(binding.imageTempToday5)
                    }
                    Date(startDate.time + 86400000*6) -> {
                        binding.lowestTempToday6.text = String.format(
                            binding.lowestTempToday6.context.getString(R.string.temperature),
                            day.minTemperature)
                        binding.maxTempToday6.text = String.format(
                            binding.maxTempToday6.context.getString(R.string.temperature),
                            day.maxTemperature)
                        binding.today6.text = days[calendar.get(Calendar.DAY_OF_WEEK)-1]
                        Glide.with(binding.imageTempToday6.context)
                            .load(day.url.toUri().buildUpon().scheme("https").build())
                            .into(binding.imageTempToday6)
                    }
                    Date(startDate.time + 86400000*7) -> {
                        binding.lowestTempToday7.text = String.format(
                            binding.lowestTempToday7.context.getString(R.string.temperature),
                            day.minTemperature)
                        binding.maxTempToday7.text = String.format(
                            binding.maxTempToday7.context.getString(R.string.temperature),
                            day.maxTemperature)
                        binding.today7.text = days[calendar.get(Calendar.DAY_OF_WEEK)-1]
                        Glide.with(binding.imageTempToday7.context)
                            .load(day.url.toUri().buildUpon().scheme("https").build())
                            .into(binding.imageTempToday7)
                    }
                }
                calendar.add(Calendar.DATE, 1)
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
            binding.feelsLikeValue.text = String.format(
                binding.feelsLikeValue.context.getString(R.string.temperature),
                todayData.feelsLike)
            binding.humidityValue.text = String.format(
                binding.humidityValue.context.getString(R.string.humidity_value),
                todayData.humidity)
            binding.windValue.text = String.format(
                binding.windValue.context.getString(R.string.wind_value),
                todayData.windSpeed)
            binding.visibilityValue.text = String.format(
                binding.visibilityValue.context.getString(R.string.visibility_value),
                todayData.visibility)
            binding.pressureValue.text = String.format(
                binding.pressureValue.context.getString(R.string.pressure_value),
                todayData.pressure)
        }
    }

    class AirQuality(private val binding: AirQualityBinding, private val onAirQualitySeeMoreClicked: (WeatherRecyclerViewItem.AirQuality) -> Unit): WeatherViewHolder(binding){
        fun bind(airQuality: WeatherRecyclerViewItem.AirQuality){
            binding.seeMoreTab.setOnClickListener {
                onAirQualitySeeMoreClicked(airQuality)
            }
            val context = binding.airQualityValue.context
            when(airQuality.aqi){
                1 -> {
                    binding.airQualityValue.text = context.getString(R.string.air_quality_good)
                    binding.airQualityDescription.text = context.getString(R.string.air_quality_good_health_information)
                    Glide.with(context).load(R.drawable.air_quality_good)
                        .into(binding.airQualityGraphic)
                }
                2 -> {
                    binding.airQualityValue.text = context.getString(R.string.air_quality_fair)
                    binding.airQualityDescription.text = context.getString(R.string.air_quality_fairModerate_health_information)
                    Glide.with(context).load(R.drawable.air_quality_fair)
                        .into(binding.airQualityGraphic)
                }
                3 -> {
                    binding.airQualityValue.text = context.getString(R.string.air_quality_moderate)
                    binding.airQualityDescription.text = context.getString(R.string.air_quality_fairModerate_health_information)
                    Glide.with(context).load(R.drawable.air_quality_moderate)
                        .into(binding.airQualityGraphic)
                }
                4 -> {
                    binding.airQualityValue.text = context.getString(R.string.air_quality_poor)
                    binding.airQualityDescription.text = context.getString(R.string.air_quality_poorVeryPoor_health_information)
                    Glide.with(context).load(R.drawable.air_quality_poor)
                        .into(binding.airQualityGraphic)
                }
                5 -> {
                    binding.airQualityValue.text = context.getString(R.string.air_quality_very_poor)
                    binding.airQualityDescription.text = context.getString(R.string.air_quality_poorVeryPoor_health_information)
                    Glide.with(context).load(R.drawable.air_quality_very_poor)
                        .into(binding.airQualityGraphic)
                }
            }
        }
    }

    class DailyAlerts(private val binding: DailyAlertsBinding, private val onDailyAlertsSeeMoreClicked: (WeatherRecyclerViewItem.DailyAlerts) -> Unit): WeatherViewHolder(binding){
        fun bind(dailyAlerts: WeatherRecyclerViewItem.DailyAlerts){
            binding.seeMoreTab.setOnClickListener {
                onDailyAlertsSeeMoreClicked(dailyAlerts)
            }
            binding.alerts.text = dailyAlerts.event
            binding.senderName.text = dailyAlerts.sender
        }
    }
}