package com.example.jpmccodingchallenge.domain

import com.example.jpmccodingchallenge.model.LocationData
import jakarta.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(country: String): Result<LocationData?> {
        return repository.getLatLng(country)
    }
}