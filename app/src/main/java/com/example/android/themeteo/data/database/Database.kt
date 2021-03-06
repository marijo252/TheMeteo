package com.example.android.themeteo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DatabaseWeather::class, DatabaseAirPollution::class], version = 2)
@TypeConverters(
    CurrentWeatherConverters::class,
    ListOfHourlyWeatherConverters::class,
    ListOfDailyWeatherConverters::class,
    ListOfAlertsConverters::class,
    ListOfAirPollutionDataConverters::class,
    CoordinatesConverters::class,
)
abstract class Database: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}