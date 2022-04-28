package com.example.android.themeteo.domains

import com.squareup.moshi.Json
import java.util.*


data class AirPollution(
    val coordinates: Coordinates,
    val airPollutionData: List<AirPollutionData>,
)

data class AirPollutionData(
    val main: MainData,
    val components: Components,
    val date: Date
)

data class MainData(
    val aqi: Int
)

data class Components(
    val co: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    val pm2_5: Double,
    val pm10: Double,
    val nh3: Double,
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double,
)