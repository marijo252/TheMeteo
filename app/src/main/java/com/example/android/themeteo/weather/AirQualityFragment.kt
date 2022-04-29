package com.example.android.themeteo.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.android.themeteo.R
import com.example.android.themeteo.databinding.FragmentAirQualityBinding
import kotlin.math.roundToInt

class AirQualityFragment : Fragment() {
    private lateinit var binding: FragmentAirQualityBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_air_quality, container, false
            )

        val airQuality = AirQualityFragmentArgs.fromBundle(requireArguments()).airQualityDetails

        binding.airQuality = airQuality

        setValuesForAqi(airQuality)
        setIndexValueForPollutant(airQuality)

        return binding.root
    }

    private fun setValuesForAqi(airQuality: WeatherRecyclerViewItem.AirQuality){
        when(airQuality.aqi){
            1 -> {
                binding.airQualityValueDetails.text = getString(R.string.air_quality_good)
                binding.airQualityMessageDescription.text = getString(R.string.air_quality_good_health_information)
                Glide.with(this).load(R.drawable.air_quality_good)
                    .into(binding.airQualityGraphic)
            }
            2 -> {
                binding.airQualityValueDetails.text = getString(R.string.air_quality_fair)
                binding.airQualityMessageDescription.text = getString(R.string.air_quality_fairModerate_health_information)
                Glide.with(this).load(R.drawable.air_quality_fair)
                    .into(binding.airQualityGraphic)
            }
            3 -> {
                binding.airQualityValueDetails.text = getString(R.string.air_quality_moderate)
                binding.airQualityMessageDescription.text = getString(R.string.air_quality_fairModerate_health_information)
                Glide.with(this).load(R.drawable.air_quality_moderate)
                    .into(binding.airQualityGraphic)
            }
            4 -> {
                binding.airQualityValueDetails.text = getString(R.string.air_quality_poor)
                binding.airQualityMessageDescription.text = getString(R.string.air_quality_poorVeryPoor_health_information)
                Glide.with(this).load(R.drawable.air_quality_poor)
                    .into(binding.airQualityGraphic)
            }
            5 -> {
                binding.airQualityValueDetails.text = getString(R.string.air_quality_very_poor)
                binding.airQualityMessageDescription.text = getString(R.string.air_quality_fairModerate_health_information)
                Glide.with(this).load(R.drawable.air_quality_very_poor)
                    .into(binding.airQualityGraphic)
            }
        }
    }

    private fun setIndexValueForPollutant(airQuality: WeatherRecyclerViewItem.AirQuality){
        when(airQuality.pm2_5){
            in 0.0..10.0 -> binding.indexLevelValuePm25.text = getString(R.string.air_quality_good)
            in 10.0..20.0 -> binding.indexLevelValuePm25.text = getString(R.string.air_quality_fair)
            in 20.0..25.0 -> binding.indexLevelValuePm25.text = getString(R.string.air_quality_moderate)
            in 25.0..50.0 -> binding.indexLevelValuePm25.text = getString(R.string.air_quality_poor)
            else -> binding.indexLevelValuePm25.text = getString(R.string.air_quality_very_poor)
        }

        when(airQuality.pm10){
            in 0.0..20.0 -> binding.indexLevelValuePm10.text = getString(R.string.air_quality_good)
            in 20.0..40.0 -> binding.indexLevelValuePm10.text = getString(R.string.air_quality_fair)
            in 40.0..50.0 -> binding.indexLevelValuePm10.text = getString(R.string.air_quality_moderate)
            in 50.0..100.0 -> binding.indexLevelValuePm10.text = getString(R.string.air_quality_poor)
            else -> binding.indexLevelValuePm10.text = getString(R.string.air_quality_very_poor)
        }

        when(airQuality.no2){
            in 0.0..40.0 -> binding.indexLevelValueNo2.text = getString(R.string.air_quality_good)
            in 40.0..90.0 -> binding.indexLevelValueNo2.text = getString(R.string.air_quality_fair)
            in 90.0..120.0 -> binding.indexLevelValueNo2.text = getString(R.string.air_quality_moderate)
            in 120.0..230.0 -> binding.indexLevelValueNo2.text = getString(R.string.air_quality_poor)
            else -> binding.indexLevelValueNo2.text = getString(R.string.air_quality_very_poor)
        }

        when(airQuality.o3){
            in 0.0..50.0 -> binding.indexLevelValueO3.text = getString(R.string.air_quality_good)
            in 50.0..100.0 -> binding.indexLevelValueO3.text = getString(R.string.air_quality_fair)
            in 100.0..130.0 -> binding.indexLevelValueO3.text = getString(R.string.air_quality_moderate)
            in 130.0..240.0 -> binding.indexLevelValueO3.text = getString(R.string.air_quality_poor)
            else -> binding.indexLevelValueO3.text = getString(R.string.air_quality_very_poor)
        }

        when(airQuality.so2){
            in 0.0..100.0 -> binding.indexLevelValueSo2.text = getString(R.string.air_quality_good)
            in 100.0..200.0 -> binding.indexLevelValueSo2.text = getString(R.string.air_quality_fair)
            in 200.0..350.0 -> binding.indexLevelValueSo2.text = getString(R.string.air_quality_moderate)
            in 350.0..500.0 -> binding.indexLevelValueSo2.text = getString(R.string.air_quality_poor)
            else -> binding.indexLevelValueSo2.text = getString(R.string.air_quality_very_poor)
        }
    }
}