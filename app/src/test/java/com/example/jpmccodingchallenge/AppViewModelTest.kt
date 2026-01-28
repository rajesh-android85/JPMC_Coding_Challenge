package com.example.jpmccodingchallenge

import com.example.jpmccodingchallenge.domain.GetLocationUseCase
import com.example.jpmccodingchallenge.domain.GetWeatherUseCase
import com.example.jpmccodingchallenge.model.AppState
import com.google.android.gms.location.FusedLocationProviderClient
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class AppViewModelTest {

    private lateinit var useCase: GetLocationUseCase
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var viewModel: AppViewModel

    @Before
    fun setup() {
        useCase = mock()
        getWeatherUseCase = mock()
        locationClient = mock()
        viewModel = AppViewModel(useCase, getWeatherUseCase, locationClient)
    }

    @Test
    fun checkInitialCountryValueIsEmptyTest() = runTest {
        val result = viewModel.country.first()
        assertEquals("", result)
    }

    @Test
    fun updateCountryValue() = runTest {
        val newCountry = "India"
        viewModel.updateCountry(newCountry)
        val result = viewModel.country.first()
        assertEquals(newCountry, result)
    }

    @Test
    fun checkInitialStateValue() = runTest {
        val result = viewModel.state.first()
        assertEquals(AppState(), result)
    }

}