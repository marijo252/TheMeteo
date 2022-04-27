package com.example.android.themeteo.weather

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.themeteo.data.DataSource
import com.example.android.themeteo.domains.Weather
import kotlinx.coroutines.launch
import com.example.android.themeteo.data.Result
import java.text.SimpleDateFormat

class WeatherViewModel(
    app: Application,
    private val dataSource: DataSource
) : ViewModel() {

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private val _weatherRecyclerView = MutableLiveData<List<WeatherRecyclerViewItem>>()
    val weatherRecyclerView: LiveData<List<WeatherRecyclerViewItem>>
        get() = _weatherRecyclerView

    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double>
        get() = _latitude

    private val _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double>
        get() = _longitude

    fun updateLocation(latitude: Double, longitude: Double) {
        _latitude.value = latitude
        _longitude.value = longitude
    }

    @SuppressLint("SimpleDateFormat")
    fun refreshWeatherAndGetData() {
        viewModelScope.launch {
            dataSource.refreshWeather(latitude.value!!, longitude.value!!)
            val result = dataSource.getWeather()
            when (result) {
                is Result.Success<*> -> {
                    val weather = result.data as Weather
                    _weather.value = weather

                    val todayWeather = WeatherRecyclerViewItem.TodayData(
                        feelsLike = weather.current.feelsLike,
                        pressure = weather.current.pressure,
                        humidity = weather.current.humidity,
                        uvi = weather.current.uvi,
                        visibility = weather.current.visibility,
                        windSpeed = weather.current.windSpeed
                    )

                    val dateFormat = SimpleDateFormat("h:mm a")

                    val TodaySunriseSunset = WeatherRecyclerViewItem.TodaySunriseSunset(
                        sunrise = dateFormat.format(weather.current.sunrise),
                        sunset = dateFormat.format(weather.current.sunset.time),
                    )

                    val DailyMeteos = WeatherRecyclerViewItem.DailyMeteos(
                        dailyData = weather.daily.map {
                            WeatherRecyclerViewItem.DailyMeteos.DailyMeteo(
                                date = it.date,
                                minTemperature = it.temperature.min,
                                maxTemperature = it.temperature.max,
                            )
                        }
                    )

                    _weatherRecyclerView.value = listOf(
                        todayWeather,
                        TodaySunriseSunset,
                        DailyMeteos,
                    )
                }
                is Result.Error ->
                    Log.e(TAG, "Error trying to get weather ")
            }
        }
    }

    companion object {
        private const val TAG = "WeatherViewModel"
    }

}