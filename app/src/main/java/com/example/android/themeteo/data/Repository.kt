package com.example.android.themeteo.data

import com.example.android.themeteo.data.api.Network
import com.example.android.themeteo.data.database.WeatherDao
import com.example.android.themeteo.data.api.entities.asDatabaseEntity
import com.example.android.themeteo.data.database.asDomain
import com.example.android.themeteo.domains.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class Repository (
    private val weatherDao: WeatherDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataSource {

    override suspend fun refreshWeather(latitude: Double,longitude: Double) {
        val weather = Network.retrofitService.getWeather(latitude, longitude)
        weatherDao.insert(weather.asDatabaseEntity())
    }

    override suspend fun getWeather(): Result<Weather> {
        return try {
            Result.Success(weatherDao.getWeather().asDomain())
        } catch (ex: Exception){
            Result.Error(ex.localizedMessage)
        }
    }

}
