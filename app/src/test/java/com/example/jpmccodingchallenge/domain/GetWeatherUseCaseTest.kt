package com.example.jpmccodingchallenge.domain

import com.example.jpmccodingchallenge.model.CountryDetailsData
import com.example.jpmccodingchallenge.model.CurrentWeatherData
import com.example.jpmccodingchallenge.model.Weather
import com.example.jpmccodingchallenge.model.WeatherDetailsData
import com.example.jpmccodingchallenge.model.WindDetailsData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetWeatherUseCaseTest {

    private lateinit var repository: WeatherRepository
    private lateinit var getWeatherUseCase: GetWeatherUseCase

    @Before
    fun setup() {
        repository = mock()
        getWeatherUseCase = GetWeatherUseCase(repository)
    }

    @Test
    fun getWeatherTest() = runTest {
        val lat = 37.7749
        val lon = -122.4194
        val expectedWeather = Weather(
            main = CurrentWeatherData(
                temp = 25.0,
                feelsLike = 25.0,
                tempMin = 25.0,
                tempMax = 25.0,
                humidity = 1
            ),
            cityName = "USA",
            sys = CountryDetailsData("USA"),
            wind = WindDetailsData(25.0),
            weather = WeatherDetailsData("")
        )

        whenever(repository.getWeather(lat, lon)).thenReturn(expectedWeather)
        val result = getWeatherUseCase(lat, lon)
        assertEquals(expectedWeather, result)
    }
}