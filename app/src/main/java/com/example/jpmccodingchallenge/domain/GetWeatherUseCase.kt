package com.example.jpmccodingchallenge.domain

import com.example.jpmccodingchallenge.model.Weather
import jakarta.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double): Weather {
        return repository.getWeather(lat, lon)
    }
}