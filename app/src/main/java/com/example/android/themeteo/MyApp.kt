package com.example.android.themeteo

import android.app.Application
import com.example.android.themeteo.data.DataSource
import com.example.android.themeteo.data.Repository
import com.example.android.themeteo.data.database.LocalDB
import com.example.android.themeteo.weather.WeatherViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val myModule = module{
            viewModel {
                WeatherViewModel(
                    get(),
                    get() as DataSource
                )
            }
            single { Repository(get()) }
            single { LocalDB.createWeatherDao(this@MyApp) }
        }

        startKoin {
            androidContext(this@MyApp)
            modules(listOf(myModule))
        }
    }
}