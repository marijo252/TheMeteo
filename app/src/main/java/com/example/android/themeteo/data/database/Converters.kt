package com.example.android.themeteo.data.database

import androidx.room.TypeConverter
import com.example.android.themeteo.data.api.NetworkDateAdapter
import com.example.android.themeteo.data.api.entities.*
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeatherConverters {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DateAdapter())
        .build()
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
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DateAdapter())
        .build()
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
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DateAdapter())
        .build()
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
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DateAdapter())
        .build()
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

class CoordinatesConverters {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val jsonAdapter: JsonAdapter<Coordinates> = moshi.adapter(Coordinates::class.java)

    @TypeConverter
    fun fromString(value: String?): Coordinates? {
        return value?.let { jsonAdapter.fromJson(it) }
    }

    @TypeConverter
    fun currentWeatherToString(value: Coordinates?): String? {
        return jsonAdapter.toJson(value)
    }
}

class ListOfAirPollutionDataConverters {
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DateAdapter())
        .build()
    private val list: Type = Types.newParameterizedType(
        List::class.java,
        AirPollutionData::class.java
    )
    private val jsonAdapter: JsonAdapter<List<AirPollutionData>> = moshi.adapter(list)

    @TypeConverter
    fun fromString(value: String?): List<AirPollutionData>? {
        return value?.let { jsonAdapter.fromJson(it) }
    }

    @TypeConverter
    fun listOfAlertsToString(value: List<AirPollutionData>?): String? {
        return jsonAdapter.toJson(value)
    }
}

class DateAdapter: JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            synchronized(dateFormat) {
                dateFormat.parse(dateAsString)
            }
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            synchronized(dateFormat) {
                writer.value(value.toString())
            }
        }
    }

    companion object {
        const val SERVER_FORMAT = ("EEE MMM dd HH:mm:ss zzz yyyy") // define your server format here
    }
}

