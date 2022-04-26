package com.example.android.themeteo.weather

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.themeteo.data.DataSource
import com.example.android.themeteo.domains.Weather
import kotlinx.coroutines.launch

class WeatherViewModel (
    app: Application,
    private val dataSource: DataSource
): ViewModel() {

    private val _weather = MutableLiveData<Weather>()

    val weather: LiveData<Weather>
        get() = _weather

    private val _latitude = MutableLiveData<Double>()

    val latitude: LiveData<Double>
        get() = _latitude

    private val _longitude = MutableLiveData<Double>()

    val longitude: LiveData<Double>
        get() = _longitude

    init {
        if(isLocationAvailable()){
            refreshWeather()
        }
    }

    fun updateLocation(latitude: Double, longitude: Double){
        _latitude.value = latitude
        _longitude.value = longitude
    }

    fun refreshWeather(){
        viewModelScope.launch {
            dataSource.refreshWeather(latitude.value!!,longitude.value!!)
        }
    }

    fun isLocationAvailable(): Boolean{
        return _latitude.value != null && _longitude.value != null
    }

}