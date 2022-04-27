package com.example.android.themeteo.data.api.entities

import com.example.android.themeteo.data.database.DatabaseWeather
import com.squareup.moshi.Json
import java.util.*

data class NetworkWeather(
    @Json(name = "lat")
    val latitude: Double,
    @Json(name = "lon")
    val longitude: Double,
    val timezone: String,
    val current: CurrentWeather,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,
    val alerts: List<Alerts>?
)

data class CurrentWeather(
    @Json(name = "dt")
    val date: Date,
    val sunrise: Date,
    val sunset: Date,
    @Json(name = "temp")
    val temperature: Double,
    @Json(name = "feels_like")
    val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    val uvi: Double,
    val visibility: Double,
    @Json(name = "wind_speed")
    val windSpeed: Double,
    @Json(name = "wind_deg")
    val windDegree: Double,
    @Json(name = "weather")
    val weatherDescription: List<WeatherDescription>
)

data class WeatherDescription(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class HourlyWeather(
    @Json(name = "dt")
    val date: Date,
    @Json(name = "temp")
    val temperature: Double,
)

data class DailyWeather(
    @Json(name = "dt")
    val date: Date,
    @Json(name = "temp")
    val temperature: TemperatureMaxMin,
    val weather: List<WeatherDescription>,
)

data class TemperatureMaxMin(
    val min: Double,
    val max: Double
)

data class Alerts(
    @Json(name = "sender_name")
    val senderName: String,
    val event: String,
    @Json(name = "start")
    val eventStart: Date,
    @Json(name = "end")
    val eventEnd: Date,
    val description: String,
)

fun NetworkWeather.asDatabaseEntity(): DatabaseWeather{
    return DatabaseWeather(
        latitude = latitude,
        longitude = longitude,
        timezone = timezone,
        current = current,
        hourly = hourly,
        daily = daily,
        alerts = alerts,
    )
}

fun CurrentWeather.asDomainCurrentWeather(): com.example.android.themeteo.domains.CurrentWeather{
    return com.example.android.themeteo.domains.CurrentWeather(
        date = date,
        sunrise = sunrise,
        sunset = sunset,
        temperature = temperature,
        feelsLike = feelsLike,
        pressure = pressure,
        humidity = humidity,
        uvi = uvi,
        visibility = visibility,
        windSpeed = windSpeed,
        windDegree = windDegree,
        weatherDescription = weatherDescription.map {
            com.example.android.themeteo.domains.WeatherDescription(
                id = it.id,
                main = it.main,
                description = it.description,
                icon = it.icon,
            )
        }
    )
}

fun List<HourlyWeather>.asDomainHourlyWeather(): List<com.example.android.themeteo.domains.HourlyWeather>{
    return map {
        com.example.android.themeteo.domains.HourlyWeather(
            date = it.date,
            temperature = it.temperature,
        )
    }
}

fun List<DailyWeather>.asDomainDailyWeather(): List<com.example.android.themeteo.domains.DailyWeather>{
    return map {
        com.example.android.themeteo.domains.DailyWeather(
            date = it.date,
            temperature = it.temperature.asDomainTemperatureMaxMin(),
            weather = it.weather.map { weatherDescription ->
                com.example.android.themeteo.domains.WeatherDescription(
                    id = weatherDescription.id,
                    main = weatherDescription.main,
                    description = weatherDescription.description,
                    icon = weatherDescription.icon,
                )
            },
        )
    }
}

fun List<Alerts>.asDomainAlerts(): List<com.example.android.themeteo.domains.Alerts>{
    return map {
        com.example.android.themeteo.domains.Alerts(
            senderName = it.senderName,
            event = it.event,
            eventStart = it.eventStart,
            eventEnd = it.eventEnd,
            description = it.description,
        )
    }
}

fun TemperatureMaxMin.asDomainTemperatureMaxMin(): com.example.android.themeteo.domains.TemperatureMaxMin{
    return com.example.android.themeteo.domains.TemperatureMaxMin(
        min = min,
        max = max,
    )
}