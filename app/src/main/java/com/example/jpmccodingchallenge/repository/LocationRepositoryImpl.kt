package com.example.jpmccodingchallenge.repository

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun getLatLng(countryName: String): Result<LocationData?> =
        suspendCancellableCoroutine { continuation ->
            geocoder.getFromLocationName(
                countryName,
                1,
                object : Geocoder.GeocodeListener {

                    override fun onGeocode(addresses: MutableList<Address>) {
                        if (addresses.isNotEmpty()) {
                            val address = addresses[0]
                            continuation.resume(

                                Result.success(LocationData(
                                        latitude = address.latitude,
                                        longitude = address.longitude
                                    ))
                                ,
                                onCancellation = null
                            )
                        } else {
                            continuation.resume( Result.failure(
                                NoSuchElementException("No address found for $countryName")
                            ), onCancellation = null)
                        }
                    }

                    override fun onError(errorMessage: String?) {
                        continuation.resume( Result.failure(
                            NoSuchElementException("No address found for $countryName")
                        ), onCancellation = null)
                    }
                }
            )
        }
}