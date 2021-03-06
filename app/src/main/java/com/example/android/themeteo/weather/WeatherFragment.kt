package com.example.android.themeteo.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.android.themeteo.BuildConfig
import com.example.android.themeteo.R
import com.example.android.themeteo.databinding.FragmentWeatherBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class WeatherFragment : Fragment() {
    val _viewModel: WeatherViewModel by viewModel()
    private lateinit var binding: FragmentWeatherBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val locationRequest = LocationRequest.create()
    private var latitude = 0.0
    private var longitude = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_weather, container, false
            )
        binding.lifecycleOwner = this
        binding.weatherViewModel = _viewModel

        if(savedInstanceState != null){
            latitude = savedInstanceState.getDouble(LATITUDE)
            longitude = savedInstanceState.getDouble(LONGITUDE)
            binding.motionLayoutContainer.transitionToState(savedInstanceState.getInt(MOTION_LAYOUT_STATE))
        }

        val weatherAdapter = WeatherAdapter(
            onAirQualitySeeMoreClicked = { airQuality ->
                this.findNavController().navigate(
                    WeatherFragmentDirections.actionWeatherFragmentToAirQualityFragment(airQuality)
                )
            },
            onDailyAlertsSeeMoreClicked = { dailyAlerts ->
                this.findNavController().navigate(
                    WeatherFragmentDirections.actionWeatherFragmentToDailyAlertsFragment(dailyAlerts)
                )
            },
        )

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weatherAdapter
        }

        _viewModel.weatherRecyclerView.observe(viewLifecycleOwner) { result ->
            weatherAdapter.items = result
        }

        _viewModel.showLoading.observe(viewLifecycleOwner){
            if(it){
                binding.statusLoadingWheel.visibility = View.VISIBLE
                binding.currentTemperature.visibility = View.GONE
                binding.city.visibility = View.GONE
                binding.currentWeatherDesc.visibility = View.GONE
            }
            else{
                binding.statusLoadingWheel.visibility = View.GONE
                binding.currentTemperature.visibility = View.VISIBLE
                binding.city.visibility = View.VISIBLE
                binding.currentWeatherDesc.visibility = View.VISIBLE
            }
        }

        _viewModel.weather.observe(viewLifecycleOwner) { weather ->
            if (weather.current.weatherDescription[0].id in 500..531) {
                setBackgroundImage(R.drawable.rainy)
            } else if (weather.current.weatherDescription[0].id in 200..232) {
                setBackgroundImage(R.drawable.thunder_storm)
            } else if (weather.current.weatherDescription[0].id in 600..622) {
                setBackgroundImage(R.drawable.snow)
            } else if (weather.current.weatherDescription[0].id in 802..804) {
                setBackgroundImage(R.drawable.cloudy)
            } else if (weather.current.date.after(Date(weather.current.sunrise.time - 3600000))
                && weather.current.date.before(weather.current.sunrise)
            ) {
                setBackgroundImage(R.drawable.sunrise)
            } else if (weather.current.date.after(Date(weather.current.sunset.time - 3600000))
                && weather.current.date.before(weather.current.sunset)
            ) {
                setBackgroundImage(R.drawable.sunset)
            } else if (weather.current.date.after(weather.current.sunrise)
                && weather.current.date.before(
                    weather.current.sunset
                )
            ) {
                if (weather.current.weatherDescription[0].id == 800) {
                    setBackgroundImage(R.drawable.sunny)
                } else if (weather.current.weatherDescription[0].id == 801) {
                    setBackgroundImage(R.drawable.sun_cloudy)
                }
            } else if (weather.current.date.after(weather.current.sunset)
                || weather.current.date.before(
                    weather.current.sunrise
                )
            ) {
                if (weather.current.weatherDescription[0].id == 800) {
                    setBackgroundImage(R.drawable.clear_night)
                } else if (weather.current.weatherDescription[0].id == 801) {
                    setBackgroundImage(R.drawable.cloudy_night)
                }
            } else{
                setBackgroundImage(R.drawable.sunny)
            }
        }

        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    checkDeviceLocationSettings()
                } else {
                    binding.locationDeniedImage.visibility = View.VISIBLE
                    binding.locationDeniedText.visibility = View.VISIBLE
                    Snackbar.make(
                        binding.root,
                        R.string.permission_denied_explanation, Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction(R.string.settings) {
                            // Displays App settings screen.
                            startActivity(Intent().apply {
                                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                data = Uri.fromParts(
                                    "package",
                                    BuildConfig.APPLICATION_ID,
                                    null)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            })
                        }.show()
                }
            }

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                binding.locationDeniedImage.visibility = View.GONE
                binding.locationDeniedText.visibility = View.GONE
                for (location in locationResult.locations) {
                    setDeviceLocation()
                }
            }
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble(LATITUDE, latitude)
        outState.putDouble(LONGITUDE, longitude)
        outState.putInt(MOTION_LAYOUT_STATE,binding.motionLayoutContainer.currentState)
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    private fun setBackgroundImage(drawable: Int) {
        Glide.with(this).load(drawable)
            .centerCrop()
            .into(binding.backgroundImage)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun checkPermissions() {
        if (foregroundPermissionApproved()) {
            checkDeviceLocationSettings()
        } else {
            requestForegroundLocationPermission()
        }
    }

    private fun foregroundPermissionApproved(): Boolean {
        return (PackageManager.PERMISSION_GRANTED ==
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ))
    }

    private fun checkDeviceLocationSettings(resolve: Boolean = true) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException && resolve) {
                try {
                    startIntentSenderForResult(
                        exception.resolution.intentSender,
                        REQUEST_CHECK_SETTINGS,
                        null,
                        0,
                        0,
                        0,
                        null
                    )
                } catch (ex: IntentSender.SendIntentException) {
                    Log.d(TAG, "Error getting location settings: " + ex.message)
                }
            } else {
                binding.locationDeniedImage.visibility = View.VISIBLE
                binding.locationDeniedText.visibility = View.VISIBLE
                Toast.makeText(requireContext(),
                    R.string.location_required_error,
                    Toast.LENGTH_LONG,
                ).show()
            }
        }

        task.addOnSuccessListener { locationSettingsResponse ->
            binding.locationDeniedImage.visibility = View.GONE
            binding.locationDeniedText.visibility = View.GONE
            setDeviceLocation()
        }

    }

    @SuppressLint("MissingPermission")
    private fun setDeviceLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    _viewModel.updateLocation(latitude, longitude)
                    _viewModel.refreshWeatherAndGetData()
                } else {
                    Log.e(TAG, "location is off")
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "exception: ${it.message}")
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            checkDeviceLocationSettings(false)
        }
    }

    private fun requestForegroundLocationPermission() {
        requestPermissionLauncher.launch(
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    companion object {
        private const val REQUEST_CHECK_SETTINGS = 29
        private const val TAG = "WeatherFragment"
        private const val LATITUDE = "Latitude"
        private const val LONGITUDE = "Longitude"
        private const val MOTION_LAYOUT_STATE = "MotionLayoutState"
    }

}