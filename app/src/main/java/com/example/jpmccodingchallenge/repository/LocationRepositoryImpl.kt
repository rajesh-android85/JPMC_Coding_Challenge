package com.example.jpmccodingchallenge.repository

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.example.jpmccodingchallenge.model.LocationData
import com.example.jpmccodingchallenge.domain.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationRepository {

    private val geocoder = Geocoder(context, Locale.getDefault())

    override suspend fun getLatLng(countryName: String): LocationData? =
        suspendCancellableCoroutine { continuation ->

            geocoder.getFromLocationName(
                countryName,
                1,
                object : Geocoder.GeocodeListener {

                    override fun onGeocode(addresses: MutableList<Address>) {
                        if (addresses.isNotEmpty()) {
                            val address = addresses[0]
                            continuation.resume(
                                LocationData(
                                    latitude = address.latitude,
                                    longitude = address.longitude
                                ),
                                onCancellation = null
                            )
                        } else {
                            continuation.resume(null, onCancellation = null)
                        }
                    }

                    override fun onError(errorMessage: String?) {
                        continuation.resume(null, onCancellation = null)
                    }
                }
            )
        }
}