package com.example.android.themeteo.weather

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
        )
    }
}