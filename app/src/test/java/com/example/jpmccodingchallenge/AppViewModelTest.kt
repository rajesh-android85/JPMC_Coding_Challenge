package com.example.jpmccodingchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.jpmccodingchallenge.domain.GetLocationUseCase
import com.example.jpmccodingchallenge.domain.GetWeatherUseCase
import com.example.jpmccodingchallenge.model.AppState
import com.example.jpmccodingchallenge.model.LocationData
import com.example.jpmccodingchallenge.model.Weather
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any

class AppViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AppViewModel
    private lateinit var getLocationUseCase: GetLocationUseCase
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var locationClient: FusedLocationProviderClient

    @Before
    fun setup() {
        getLocationUseCase = mock(GetLocationUseCase::class.java)
        getWeatherUseCase = mock(GetWeatherUseCase::class.java)
        locationClient = mock(FusedLocationProviderClient::class.java)
        viewModel = AppViewModel(
            useCase = getLocationUseCase,
            getWeatherUseCase = getWeatherUseCase,
            locationClient = locationClient
        )
    }

    @Test
    fun updateCountryTest() = runTest {
        viewModel.updateCountry("India")
        assertEquals("India", viewModel.country.value)
    }

    @Test
    fun fetchLocationSuccessScenarioTest() = runTest {
        val location = LocationData(latitude = 12.0, longitude = 77.0)
        val weather = mock(Weather::class.java)
        `when`(getLocationUseCase.invoke("India")).thenReturn(Result.success(location))
        `when`(getWeatherUseCase.invoke(12.0, 77.0)).thenReturn(Result.success(weather))
        viewModel.fetchLocation("India")
        assertEquals(
            AppState(weather = weather),
            viewModel.state.value
        )
    }

    @Test
    fun fetchLocationFailureScenarioTest()= runTest {
        val exception = Exception("Location not found")
        `when`(getLocationUseCase.invoke("Unknown"))
            .thenReturn(Result.failure(exception))
        viewModel.fetchLocation("Unknown")
        assertEquals(
            AppState(locationError = "Location not found"),
            viewModel.state.value
        )
    }

    @Test
    fun fetchWeatherSuccessScenarioTest() = runTest {
        val weather = mock(Weather::class.java)
        `when`(getWeatherUseCase.invoke(10.0, 20.0)).thenReturn(Result.success(weather))
        viewModel.fetchWeather(10.0, 20.0)
        assertEquals(
            AppState(weather = weather),
            viewModel.state.value
        )
    }

    @Test
    fun fetchWeatherFailureScenarioTest() = runTest {
        `when`(getWeatherUseCase.invoke(any(), any()))
            .thenReturn(Result.failure(RuntimeException("API failure")))
        viewModel.fetchWeather(10.0, 20.0)
        assertEquals(
            "API failure",
            viewModel.state.value.weatherError
        )
    }
}