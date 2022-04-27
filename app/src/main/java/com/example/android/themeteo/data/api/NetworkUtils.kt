package com.example.android.themeteo.data.api

import com.squareup.moshi.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


object Constants {
    const val API_KEY = "d0138b4c07bb7f856bf1efb5c326f427"
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val UNITS = "metric"
    const val EXCLUDE = "minutely"
}

class NetworkDateAdapter: JsonAdapter<Date>(){
    @Synchronized
    @Throws(IOException::class)
    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        val date = Date(reader.nextLong() * 1000)
        return date
    }

    @Synchronized
    @Throws(IOException::class)
    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        writer.value(value?.time?.div(1000))
    }
}