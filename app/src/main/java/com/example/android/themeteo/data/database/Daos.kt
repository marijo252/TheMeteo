package com.example.android.themeteo.data.database

import androidx.room.*

@Dao
interface WeatherDao {

    @Query("SELECT * FROM Weather ORDER BY id DESC LIMIT 1")
    suspend fun getWeather(): DatabaseWeather

    @Query("SELECT * FROM AirPollution ORDER BY id DESC LIMIT 1")
    suspend fun getAirPollution(): DatabaseAirPollution

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: DatabaseWeather)

    @Query("DELETE FROM Weather")
    suspend fun clearWeather()

    @Query("DELETE FROM AirPollution")
    suspend fun clearAirPollution()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(airPollution: DatabaseAirPollution)

    @Transaction
    suspend fun clearAndInsertWeather(weather: DatabaseWeather){
        clearWeather()
        insert(weather)
    }

    @Transaction
    suspend fun clearAndInsertAirPollution(airPollution: DatabaseAirPollution){
        clearAirPollution()
        insert(airPollution)
    }
}