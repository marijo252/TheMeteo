package com.example.android.themeteo.domains

import java.util.*

data class Weather(
    val latitude: Double,
    val longitude: Double,
    val timeZone: String,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,
    val alerts: List<Alerts>
)

data class CurrentWeather(
    val date: Date,
    val sunrise: Date,
    val sunset: Date,
    val temperature: Double,
    val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    val uvi: Double,
    val visibility: Double,
    val windSpeed: Double,
    val windDegree: Double,
    val weatherDescription: List<WeatherDescription>
)

data class WeatherDescription(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class HourlyWeather(
    val date: Date,
    val temperature: Double,
)

data class DailyWeather(
    val date: Date,
    val temperature: TemperatureMaxMin,
)

data class TemperatureMaxMin(
    val min: Double,
    val max: Double
)

data class Alerts(
    val senderName: String,
    val event: String,
    val eventStart: Date,
    val eventEnd: Date,
    val description: String,
)
