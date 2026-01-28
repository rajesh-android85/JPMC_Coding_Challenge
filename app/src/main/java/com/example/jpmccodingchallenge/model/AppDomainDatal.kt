package com.example.jpmccodingchallenge.model

data class LocationData(
    val latitude: Double,
    val longitude: Double
)

data class Weather(
    val main: CurrentWeatherData,
    val cityName: String,
    val sys: CountryDetailsData,
    val wind: WindDetailsData,
    val weather: WeatherDetailsData
)

data class WeatherDetailsData(
    val icon: String
)

data class WindDetailsData(
    val speed: Double,
)

data class CountryDetailsData(
    val country: String,
)

data class CurrentWeatherData(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int
)
