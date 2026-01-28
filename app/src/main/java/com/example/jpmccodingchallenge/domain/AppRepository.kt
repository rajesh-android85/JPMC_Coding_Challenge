package com.example.jpmccodingchallenge.domain

import com.example.jpmccodingchallenge.model.LocationData
import com.example.jpmccodingchallenge.model.Weather

interface LocationRepository {
    suspend fun getLatLng(countryName: String): LocationData?
}

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Weather
}