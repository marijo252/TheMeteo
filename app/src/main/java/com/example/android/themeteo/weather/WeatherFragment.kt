package com.example.android.themeteo.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.themeteo.BuildConfig
import com.example.android.themeteo.R
import com.example.android.themeteo.databinding.FragmentWeatherBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class WeatherFragment : Fragment() {
    val _viewModel: WeatherViewModel by viewModel()
    private lateinit var binding: FragmentWeatherBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val locationRequest = LocationRequest.create()
    private val weatherAdapter = WeatherAdapter()

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

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = weatherAdapter
        }

        _viewModel.weatherRecyclerView.observe(viewLifecycleOwner){ result ->
            weatherAdapter.items = result
        }

        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    checkDeviceLocationSettings()
                } else {
                    Snackbar.make(
                        binding.root,
                        R.string.permission_denied_explanation, Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction(R.string.settings) {
                            // Displays App settings screen.
                            startActivity(Intent().apply {
                                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            })
                        }.show()
                }
            }

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    setDeviceLocation()
                }
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
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
            } else{
                Snackbar.make(
                    binding.root,
                    R.string.location_required_error, Snackbar.LENGTH_INDEFINITE
                ).setAction(android.R.string.ok) {
                    checkDeviceLocationSettings()
                }.show()
            }
        }

        task.addOnSuccessListener { locationSettingsResponse ->
            setDeviceLocation()
        }

    }

    @SuppressLint("MissingPermission")
    private fun setDeviceLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    _viewModel.updateLocation(location.latitude, location.longitude)
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
            Manifest.permission.ACCESS_FINE_LOCATION)
    }

    companion object {
        private const val REQUEST_CHECK_SETTINGS = 29
        private const val TAG = "WeatherFragment"
        private const val REQUEST_FOREGROUND_PERMISSION_REQUEST_CODE = 30
    }

}