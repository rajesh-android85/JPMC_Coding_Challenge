package com.example.jpmccodingchallenge

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpmccodingchallenge.model.AppIntent
import com.example.jpmccodingchallenge.model.AppState
import com.example.jpmccodingchallenge.domain.GetLocationUseCase
import com.example.jpmccodingchallenge.domain.GetWeatherUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class AppViewModel @Inject constructor (
    private val useCase: GetLocationUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val locationClient: FusedLocationProviderClient
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    fun processIntent(intent: AppIntent) {
        when (intent) {
            is AppIntent.FetchLatLng -> fetchLocation(intent.country)
            is AppIntent.LoadWeather -> loadWeather()

        }
    }

    @SuppressLint("MissingPermission")
    private fun loadWeather() {
        viewModelScope.launch {
            _state.value = AppState(isLoadingWeather =  true)
            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    fetchWeather(it.latitude, it.longitude)
                } ?: run {
                    _state.value = AppState(weatherError = "Location not found")
                }
            }
        }
    }


    private fun fetchLocation(country: String) {
        viewModelScope.launch {
            _state.value = AppState(isLoadingLocation = true)

            val result = useCase(country)

            if (result != null) {
                fetchWeather(result.latitude, result.longitude)
            } else {
                _state.value = AppState(locationError = "Location not found")
            }
        }
    }

    private fun fetchWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val weather = getWeatherUseCase(lat, lon)
                _state.value = AppState(weather = weather)
            } catch (e: Exception) {
                _state.value = AppState(weatherError = e.message)
            }
        }
    }
}