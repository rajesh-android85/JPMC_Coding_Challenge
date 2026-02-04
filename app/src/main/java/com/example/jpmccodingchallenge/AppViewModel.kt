package com.example.jpmccodingchallenge

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpmccodingchallenge.model.AppState
import com.example.jpmccodingchallenge.domain.GetLocationUseCase
import com.example.jpmccodingchallenge.domain.GetWeatherUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


@HiltViewModel
class AppViewModel @Inject constructor(
    private val useCase: GetLocationUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val locationClient: FusedLocationProviderClient
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    private val _country = MutableStateFlow("")
    val country = _country.asStateFlow()

    fun updateCountry(countryName: String) {
        _country.value = countryName
    }


    @SuppressLint("MissingPermission")
    fun loadWeather() {
        viewModelScope.launch {
            _state.value = AppState(isLoadingWeather = true)
            if (_country.value.trim().isNotEmpty()) {
                fetchLocation(_country.value.trim())
            } else {
                val location = getLastKnownLocation()
                fetchWeather(location.latitude, location.longitude)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastKnownLocation(): Location =
        suspendCancellableCoroutine { cont ->
            locationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) cont.resume(location)
                    else cont.resumeWithException(Exception("Location not found"))
                }
                .addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }


    suspend fun fetchLocation(country: String) {
        _state.value = AppState(isLoadingLocation = true)
        useCase(country).onSuccess {
            fetchWeather(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
        }.onFailure {
            _state.value = AppState(locationError = "Location not found")
        }
    }

    suspend fun fetchWeather(lat: Double, lon: Double) {
        val weather = getWeatherUseCase(lat, lon)
        weather.onSuccess {
            _state.value = AppState( weather = it)
        }.onFailure {
            _state.value = AppState(weatherError = it.message)
        }
    }
}