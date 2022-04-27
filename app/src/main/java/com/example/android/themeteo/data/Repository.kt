package com.example.android.themeteo.data

import android.util.Log
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
        try{
            val weather = Network.retrofitService.getWeather(latitude, longitude)
            weatherDao.insert(weather.asDatabaseEntity())
        }catch (ex: Exception){
            Log.e(TAG,"error: $ex")
        }
    }

    override suspend fun getWeather(): Result<Weather> {
        return try {
            val weatherDomain = weatherDao.getWeather().asDomain()
            Result.Success(weatherDomain)
        } catch (ex: Exception){
            Log.e(TAG,"error: $ex")
            Result.Error(ex.localizedMessage)
        }
    }
    companion object{
        private const val TAG = "Repository"
    }

}
