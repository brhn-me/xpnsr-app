package com.yourapp.data.repository

import android.content.Context
import android.location.Geocoder
import android.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class LocationRepository(private val context: Context) {

    suspend fun getLocation(): Pair<Double, Double> = suspendCancellableCoroutine { continuation ->
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        val locationCallback = object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    continuation.resume(Pair(location.latitude, location.longitude))
                } else {
                    continuation.resumeWithException(RuntimeException("Location not found"))
                }
                fusedLocationProviderClient.removeLocationUpdates(this)
            }
        }
    }

    suspend fun getCityName(lat: Double, lon: Double): String = withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            if (!addresses.isNullOrEmpty()) {

            }
            return@withContext if (!addresses.isNullOrEmpty()) {
                addresses[0].locality ?: "Unknown"
            } else {
                "Unknown"
            }
        } catch (e: Exception) {
            "Unknown Location"
        }
    }
}
