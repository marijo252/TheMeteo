package com.example.android.themeteo.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

sealed class WeatherRecyclerViewItem {

    data class TodayData(
        val feelsLike: Double,
        val pressure: Double,
        val humidity: Double,
        val uvi: Double,
        val visibility: Double,
        val windSpeed: Double,
    ) : WeatherRecyclerViewItem()

    data class TodaySunriseSunset(
        val sunrise: String,
        val sunset: String,
    ) : WeatherRecyclerViewItem()

    data class DailyMeteos(
        val dailyData: List<DailyMeteo>,
    ) : WeatherRecyclerViewItem(){
        data class DailyMeteo(
            val date: Date,
            val minTemperature: Double,
            val maxTemperature: Double,
            val url: String
        )
    }

    @Parcelize
    data class AirQuality(
        val aqi: Int,
        val co: Double,
        val no: Double,
        val no2: Double,
        val o3: Double,
        val so2: Double,
        val pm2_5: Double,
        val pm10: Double,
        val nh3: Double,
    ) : Parcelable, WeatherRecyclerViewItem()
}