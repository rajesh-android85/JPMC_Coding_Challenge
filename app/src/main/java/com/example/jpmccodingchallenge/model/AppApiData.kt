package com.example.jpmccodingchallenge.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val main: CurrentWeather,
    @SerializedName("name") val cityName: String,
    val sys: CountryDetails,
    val wind: WindDetails,
    val weather: List<WeatherDetails>
)

data class WeatherDetails(
    val icon: String
)

data class WindDetails(
    val speed: Double,
)

data class CountryDetails(
    val country: String,
)

data class CurrentWeather(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val humidity: Int
)






