package com.example.android.themeteo.data.api

import com.example.android.themeteo.data.api.Constants.API_KEY
import com.example.android.themeteo.data.api.Constants.EXCLUDE
import com.example.android.themeteo.data.api.Constants.UNITS
import com.example.android.themeteo.data.api.entities.NetworkWeather
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String = EXCLUDE,
        @Query("appid") appId: String = API_KEY,
        @Query("units") units: String = UNITS
    ) : NetworkWeather
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network{
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    val retrofitService = retrofit.create(OpenWeatherService::class.java)
}
