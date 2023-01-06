package com.caesar84mx.swensonforecast.util.providers

import android.location.Location
import android.location.LocationManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

interface LocationProvider {
    val currentLocation: StateFlow<Location?>

    fun listenLocations(): Flow<Unit>
    fun onDispose()

    companion object {
        val mock = object : LocationProvider {
            override val currentLocation: StateFlow<Location?>
                get() = MutableStateFlow(
                    Location(LocationManager.GPS_PROVIDER).apply {
                        latitude = -34.929075
                        longitude = -56.160620
                    }
                )

            override fun listenLocations(): Flow<Unit> = flow { }

            override fun onDispose() { }

        }
    }
}