package com.example.android.themeteo.data.database

import android.content.Context
import androidx.room.Room

object LocalDB {
    fun createWeatherDao(context: Context): WeatherDao {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java, "weather.db"
        ).build().weatherDao()
    }
}