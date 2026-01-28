package com.example.jpmccodingchallenge.repository

import com.example.jpmccodingchallenge.BuildConfig
import com.example.jpmccodingchallenge.model.CountryDetailsData
import com.example.jpmccodingchallenge.model.CurrentWeatherData
import com.example.jpmccodingchallenge.model.Weather
import com.example.jpmccodingchallenge.model.WindDetailsData
import com.example.jpmccodingchallenge.domain.WeatherRepository
import com.example.jpmccodingchallenge.model.WeatherDetailsData
import jakarta.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double): Weather {
        val response = api.getWeather(
            lat = lat,
            lon = lon,
            apiKey = BuildConfig.WEATHER_API_KEY
        )

        return if (response.isSuccessful) {
            val body = response.body()!!
            Weather(
                main = CurrentWeatherData(
                    temp = body.main.temp,
                    feelsLike = body.main.feelsLike,
                    tempMin = body.main.tempMin,
                    tempMax = body.main.tempMax,
                    humidity = body.main.humidity
                ),
                cityName = body.cityName,
                sys = CountryDetailsData(body.sys.country),
                wind = WindDetailsData(body.wind.speed),
                weather = WeatherDetailsData(body.weather.get(0).icon)
            )
        } else {
            throw RuntimeException("API Error: ${response.code()} ${response.message()}")
        }
    }
}