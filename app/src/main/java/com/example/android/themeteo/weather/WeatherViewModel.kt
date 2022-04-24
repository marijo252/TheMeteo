package com.example.android.themeteo.weather

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.themeteo.data.DataSource
import com.example.android.themeteo.domains.Weather

class WeatherViewModel (
    app: Application,
    private val dataSource: DataSource
): ViewModel() {

    val weather = MutableLiveData<Weather>()



}