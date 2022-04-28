package com.example.android.themeteo.data

import com.example.android.themeteo.domains.AirPollution
import com.example.android.themeteo.domains.Weather

interface DataSource {
    suspend fun refreshWeather(latitude: Double,longitude: Double)
    suspend fun getWeather() : Result<Weather>
    suspend fun getAirPollution() : Result<AirPollution>
}