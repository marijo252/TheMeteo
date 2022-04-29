package com.example.android.themeteo.data.api.entities

import com.example.android.themeteo.data.database.DatabaseAirPollution
import com.squareup.moshi.Json
import java.util.*

data class NetworkAirPollution(
    @Json(name = "coord")
    val coordinates: Coordinates,
    @Json(name = "list")
    val airPollutionData: List<AirPollutionData>,
)

data class AirPollutionData(
    val main: MainData,
    val components: Components,
    @Json(name = "dt")
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
    @Json(name = "lat")
    val latitude: Double,
    @Json(name = "lon")
    val longitude: Double,
)

fun NetworkAirPollution.asDatabaseEntity() : DatabaseAirPollution{
    return DatabaseAirPollution(
        coordinates = coordinates,
        airPollutionData = airPollutionData,
    )
}

fun Coordinates.asDomainCoordinates() : com.example.android.themeteo.domains.Coordinates{
    return com.example.android.themeteo.domains.Coordinates(
        latitude = latitude,
        longitude = longitude,
    )
}

fun List<AirPollutionData>.asDomainAirPolllutionData() : List<com.example.android.themeteo.domains.AirPollutionData>{
    return map {
        com.example.android.themeteo.domains.AirPollutionData(
            main = it.main.asDomainMain(),
            components = it.components.asDomainComponents(),
            date = it.date
        )
    }
}

fun MainData.asDomainMain() : com.example.android.themeteo.domains.MainData{
    return com.example.android.themeteo.domains.MainData(
        aqi = aqi
    )
}

fun Components.asDomainComponents() : com.example.android.themeteo.domains.Components{
    return com.example.android.themeteo.domains.Components(
        co = co,
        no = no,
        no2 = no2,
        o3 = o3,
        so2 = so2,
        pm2_5 = pm2_5,
        pm10 = pm10,
        nh3 = nh3,
    )
}
