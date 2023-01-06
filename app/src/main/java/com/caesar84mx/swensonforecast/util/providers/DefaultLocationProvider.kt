package com.caesar84mx.swensonforecast.util.providers

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class DefaultLocationProvider(private val context: Context): LocationProvider {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO) + Job()
    private lateinit var locationManager: LocationManager

    private val gpsListener = LocationListener {
        scope.launch { _currentLocation.emit(it) }
    }

    private val networkListener = LocationListener {
        scope.launch { _currentLocation.emit(it) }
    }

    private val _currentLocation = MutableStateFlow<Location?>(null)
    override val currentLocation: StateFlow<Location?>
        get() = _currentLocation.asStateFlow()

    @SuppressLint("MissingPermission")
    override fun listenLocations(): Flow<Unit> = flow {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsListener
            )
        }

        if (hasNetwork) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0F,
                networkListener
            )
        }
    }

    override fun onDispose() {
        scope.cancel()
    }
}