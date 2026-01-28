package com.example.jpmccodingchallenge.model

sealed class AppIntent {
    data class FetchLatLng(val country: String) : AppIntent()
    object LoadWeather : AppIntent()
}

data class AppState(
    val isLoadingLocation: Boolean = false,
    val location: LocationData? = null,
    val locationError: String? = null,
    val isLoadingWeather: Boolean = false,
    val weather: Weather? = null,
    val weatherError: String? = null
)
