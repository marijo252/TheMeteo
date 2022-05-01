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
import com.example.android.themeteo.domains.AirPollution
import java.text.SimpleDateFormat

class WeatherViewModel(
    app: Application,
    private val dataSource: DataSource
) : ViewModel() {

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    private val _airPollution = MutableLiveData<AirPollution>()

    private val _weatherRecyclerView = MutableLiveData<MutableList<WeatherRecyclerViewItem>>()
    val weatherRecyclerView: LiveData<MutableList<WeatherRecyclerViewItem>>
        get() = _weatherRecyclerView

    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double>
        get() = _latitude

    private val _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double>
        get() = _longitude

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    fun updateLocation(latitude: Double, longitude: Double) {
        _latitude.value = latitude
        _longitude.value = longitude
    }

    init {
        _showLoading.value = true
    }

    @SuppressLint("SimpleDateFormat")
    fun refreshWeatherAndGetData() {
        viewModelScope.launch {
            dataSource.refreshWeather(latitude.value!!, longitude.value!!)
            val weatherResult = dataSource.getWeather()
            val airPollutionResult = dataSource.getAirPollution()
            _showLoading.postValue(false)
            when (weatherResult) {
                is Result.Success<*> -> {
                    val weather = weatherResult.data as Weather
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

                    val todaySunriseSunset = WeatherRecyclerViewItem.TodaySunriseSunset(
                        sunrise = dateFormat.format(weather.current.sunrise),
                        sunset = dateFormat.format(weather.current.sunset.time),
                    )

                    val dailyMeteos = WeatherRecyclerViewItem.DailyMeteos(
                        dailyData = weather.daily.map {
                            WeatherRecyclerViewItem.DailyMeteos.DailyMeteo(
                                date = it.date,
                                minTemperature = it.temperature.min,
                                maxTemperature = it.temperature.max,
                                url = "http://openweathermap.org/img/wn/${it.weather[0].icon}@2x.png"
                            )
                        }
                    )
                    _weatherRecyclerView.value = mutableListOf(
                        todayWeather,
                        todaySunriseSunset,
                        dailyMeteos,
                    )
                    if(weather.alerts != null){
                        val dateFormatForDailyAlerts = SimpleDateFormat("dd-MM-yyyy h:mm a")
                        val dailyAlerts = WeatherRecyclerViewItem.DailyAlerts(
                            sender = weather.alerts[0].senderName,
                            event = weather.alerts[0].event,
                            start = dateFormatForDailyAlerts.format(weather.alerts[0].eventStart),
                            end = dateFormatForDailyAlerts.format(weather.alerts[0].eventEnd),
                            description = weather.alerts[0].description
                        )
                        _weatherRecyclerView.value?.add(0,dailyAlerts)
                    }
                }
                is Result.Error ->
                    Log.e(TAG, "Error trying to get weather ")
            }
            when(airPollutionResult){
                is Result.Success<*> -> {
                    val airPollution = airPollutionResult.data as AirPollution
                    _airPollution.value = airPollution

                    val airQuality = WeatherRecyclerViewItem.AirQuality(
                        aqi = airPollution.airPollutionData[0].main.aqi,
                        co = airPollution.airPollutionData[0].components.co,
                        no = airPollution.airPollutionData[0].components.no,
                        no2 = airPollution.airPollutionData[0].components.no2,
                        o3 = airPollution.airPollutionData[0].components.o3,
                        so2 = airPollution.airPollutionData[0].components.so2,
                        pm2_5 = airPollution.airPollutionData[0].components.pm2_5,
                        pm10 = airPollution.airPollutionData[0].components.pm10,
                        nh3 = airPollution.airPollutionData[0].components.nh3,
                    )
                    _weatherRecyclerView.value?.add(airQuality)
                }
                is Result.Error ->
                    Log.e(TAG, "Error trying to get air Pollution ")
            }
        }
    }

    companion object {
        private const val TAG = "WeatherViewModel"
    }

}