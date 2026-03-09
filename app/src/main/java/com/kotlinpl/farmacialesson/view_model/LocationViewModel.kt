package com.kotlinpl.farmacialesson.view_model

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.activity.result.IntentSenderRequest
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.kotlinpl.farmacialesson.util.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor (
    @ApplicationContext private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {
    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                _location.update {
                    Location(
                        lat = location.latitude,
                        long = location.longitude
                    )
                }
            } else {
                val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                    .setMaxUpdates(1)
                    .build()

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            _location.update {
                                it!!.copy(
                                    lat = result.lastLocation!!.latitude,
                                    long = result.lastLocation!!.longitude
                                )
                            }
                        }
                    },
                    context.mainLooper
                )
            }
        }
    }

    fun onPermissionResult() {}

    fun hasLocationPermission() :Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkLocationSettings(
        onSettingDisable: (IntentSenderRequest) -> Unit,
        onSettingEnable: () -> Unit
    ) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .build()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            onSettingEnable()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                    onSettingDisable(intentSenderRequest)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}