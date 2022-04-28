package com.example.android.themeteo.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.themeteo.data.api.entities.*
import com.example.android.themeteo.domains.AirPollution
import com.example.android.themeteo.domains.Weather
import com.squareup.moshi.Json


@Entity(tableName = "Weather")
data class DatabaseWeather constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")val id: Long = 0L,
    @ColumnInfo(name = "latitude")val latitude: Double,
    @ColumnInfo(name = "longitude")val longitude: Double,
    @ColumnInfo(name = "timeZone")val timezone: String,
    @ColumnInfo(name = "current")val current: CurrentWeather,
    @ColumnInfo(name = "hourly")val hourly: List<HourlyWeather>,
    @ColumnInfo(name = "daily")val daily: List<DailyWeather>,
    @ColumnInfo(name = "alerts")val alerts: List<Alerts>?,
)

@Entity(tableName = "AirPollution")
data class DatabaseAirPollution constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")val id: Long = 0L,
    @ColumnInfo(name = "coordinates")val coordinates: Coordinates,
    @ColumnInfo(name = "airPollutionData")val airPollutionData: List<AirPollutionData>,
)

fun DatabaseWeather.asDomain(): Weather{
    return Weather(
        latitude = latitude,
        longitude = longitude,
        timezone = timezone,
        current = current.asDomainCurrentWeather(),
        hourly = hourly.asDomainHourlyWeather(),
        daily = daily.asDomainDailyWeather(),
        alerts = alerts?.asDomainAlerts(),
    )
}

fun DatabaseAirPollution.asDomain(): AirPollution{
    return AirPollution(
        coordinates = coordinates.asDomainCoordinates(),
        airPollutionData = airPollutionData.asDomainAirPolllutionData(),
    )
}