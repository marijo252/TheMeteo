package com.example.android.themeteo.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM Weather ORDER BY id DESC LIMIT 1")
    suspend fun getWeather(): DatabaseWeather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: DatabaseWeather)

    @Query("SELECT * FROM AirPollution ORDER BY id DESC LIMIT 1")
    suspend fun getAirPollution(): DatabaseAirPollution

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(airPollution: DatabaseAirPollution)
}