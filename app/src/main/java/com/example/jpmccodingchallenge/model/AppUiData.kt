package com.example.jpmccodingchallenge.model

data class AppState(
    val isLoadingLocation: Boolean = false,
    val location: LocationData? = null,
    val locationError: String? = null,
    val isLoadingWeather: Boolean = false,
    val weather: Weather? = null,
    val weatherError: String? = null
)
