package com.example.android.themeteo.data.database

import androidx.room.TypeConverter
import com.example.android.themeteo.data.api.entities.Alerts
import com.example.android.themeteo.data.api.entities.CurrentWeather
import com.example.android.themeteo.data.api.entities.DailyWeather
import com.example.android.themeteo.data.api.entities.HourlyWeather
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class CurrentWeatherConverters {
    private val moshi: Moshi = Moshi.Builder().build()
    private val jsonAdapter: JsonAdapter<CurrentWeather> = moshi.adapter(CurrentWeather::class.java)

    @TypeConverter
    fun fromString(value: String?): CurrentWeather? {
        return value?.let { jsonAdapter.fromJson(it) }
    }

    @TypeConverter
    fun currentWeatherToString(value: CurrentWeather?): String? {
        return jsonAdapter.toJson(value)
    }
}

class ListOfHourlyWeatherConverters {
    private val moshi: Moshi = Moshi.Builder().build()
    private val list: Type = Types.newParameterizedType(
        List::class.java,
        HourlyWeather::class.java
    )
    private val jsonAdapter: JsonAdapter<List<HourlyWeather>> =
        moshi.adapter(list)

    @TypeConverter
    fun fromString(value: String?): List<HourlyWeather>? {
        return value?.let { jsonAdapter.fromJson(it) }
    }

    @TypeConverter
    fun listOfHourlyWeatherToString(value: List<HourlyWeather>?): String? {
        return jsonAdapter.toJson(value)
    }
}

class ListOfDailyWeatherConverters {
    private val moshi: Moshi = Moshi.Builder().build()
    private val list: Type = Types.newParameterizedType(
        List::class.java,
        DailyWeather::class.java
    )
    private val jsonAdapter: JsonAdapter<List<DailyWeather>> = moshi.adapter(list)


    @TypeConverter
    fun fromString(value: String?): List<DailyWeather>? {
        return value?.let { jsonAdapter.fromJson(it) }
    }

    @TypeConverter
    fun listOfDailyWeatherToString(value: List<DailyWeather>?): String? {
        return jsonAdapter.toJson(value)
    }
}

class ListOfAlertsConverters {
    private val moshi: Moshi = Moshi.Builder().build()
    private val list: Type = Types.newParameterizedType(
        List::class.java,
        Alerts::class.java
    )
    private val jsonAdapter: JsonAdapter<List<Alerts>> = moshi.adapter(list)

    @TypeConverter
    fun fromString(value: String?): List<Alerts>? {
        return value?.let { jsonAdapter.fromJson(it) }
    }

    @TypeConverter
    fun listOfAlertsToString(value: List<Alerts>?): String? {
        return jsonAdapter.toJson(value)
    }
}