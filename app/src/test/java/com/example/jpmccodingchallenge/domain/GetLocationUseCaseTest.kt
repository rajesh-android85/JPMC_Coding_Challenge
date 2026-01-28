package com.example.jpmccodingchallenge.domain

import com.example.jpmccodingchallenge.model.LocationData
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetLocationUseCaseTest {

    private lateinit var repository: LocationRepository
    private lateinit var getLocationUseCase: GetLocationUseCase

    @Before
    fun setup() {
        repository = mock()
        getLocationUseCase = GetLocationUseCase(repository)
    }

    @Test
    fun getLatLngPositiveTest() = runTest {
        val country = "USA"
        val expectedLocation = LocationData(latitude = 37.7749, longitude = -122.4194)
        whenever(repository.getLatLng(country)).thenReturn(expectedLocation)
        val result = getLocationUseCase(country)
        assertEquals(expectedLocation, result)
    }

    @Test
    fun getLatLngNegativeTest() = runTest {
        val country = "Unknown"
        whenever(repository.getLatLng(country)).thenReturn(null)
        val result = getLocationUseCase(country)
        assertNull(result)
    }
}