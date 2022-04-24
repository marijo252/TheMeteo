package com.example.android.themeteo.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.themeteo.databinding.FragmentWeatherBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : Fragment() {
    val _viewModel: WeatherViewModel by viewModel()
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedInstanceState: Bundle?
    ) : View? {

    }
}