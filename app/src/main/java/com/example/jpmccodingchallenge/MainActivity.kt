package com.example.jpmccodingchallenge


import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.activity.viewModels
import androidx.compose.ui.res.stringResource
import com.example.jpmccodingchallenge.ui.theme.JPMCCodingChallengTheme
import com.example.jpmccodingchallenge.ui.view.CountryInputView
import com.example.jpmccodingchallenge.ui.view.ErrorMessageView
import com.example.jpmccodingchallenge.ui.view.WeatherTableView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: AppViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestLocationPermission()

        setContent {
            JPMCCodingChallengTheme {
                val country by viewModel.country.collectAsStateWithLifecycle()
                val state by viewModel.state.collectAsStateWithLifecycle()

                Scaffold(
                    topBar = { TopAppBar(title = { Text(stringResource(R.string.app_name)) }) }
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {

                        CountryInputView(
                            country = country,
                            onCountryChange = { viewModel.updateCountry(it)  },
                            onSearchClick = {
                                viewModel.fetchLocation(country)
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        when {
                            state.isLoadingLocation || state.isLoadingWeather -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            state.locationError != null -> {
                                ErrorMessageView(state.locationError.toString())
                            }

                            state.weatherError != null -> {
                                ErrorMessageView(state.weatherError.toString())
                            }

                            state.location != null -> {
                                ErrorMessageView(stringResource(R.string.error_location_not_found))
                            }

                            state.weather != null -> {
                                WeatherTableView(state.weather)
                            }
                        }
                    }
                }
            }
        }
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (granted) {
                getCurrentLocation()
            }
        }

    private fun requestLocationPermission() {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    viewModel.loadWeather()
                }
            }
            .addOnFailureListener {
                viewModel.loadWeather()
            }
    }
}