package com.example.android.themeteo.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.themeteo.R
import com.example.android.themeteo.databinding.FragmentAirQualityBinding
import com.example.android.themeteo.databinding.FragmentDailyAlertsBinding

class DailyAlertsFragment : Fragment() {
    private lateinit var binding: FragmentDailyAlertsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_daily_alerts, container, false
            )
        val dailyAlerts = DailyAlertsFragmentArgs.fromBundle(requireArguments()).dailyAlertsDetails

        binding.dailyAlerts = dailyAlerts

        return binding.root
    }
}