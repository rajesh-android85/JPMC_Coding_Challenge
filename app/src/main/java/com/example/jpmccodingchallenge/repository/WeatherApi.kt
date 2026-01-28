package com.example.jpmccodingchallenge.repository

import com.example.jpmccodingchallenge.BuildConfig
import com.example.jpmccodingchallenge.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = BuildConfig.WEATHER_API_KEY
    ): Response<WeatherResponse>
}