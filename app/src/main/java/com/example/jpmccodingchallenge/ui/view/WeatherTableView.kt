package com.example.jpmccodingchallenge.ui.view

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jpmccodingchallenge.R
import com.example.jpmccodingchallenge.model.Weather

@Composable
fun WeatherTableView(weather: Weather?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val url = "https://openweathermap.org/img/wn/${weather?.weather?.icon}@2x.png"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = url,
                contentDescription = "Weather icon",
                modifier = Modifier.size(64.dp)
                    .background(Color.LightGray)
            )
        }

        TableRow(R.string.label_country , weather?.sys?.country ?: "")
        TableRow(R.string.label_city , weather?.cityName ?: "")
        TableRow(R.string.label_temperature , weather?.main?.temp.toString())
        TableRow(R.string.label_feels_like, weather?.main?.feelsLike.toString())
        TableRow(R.string.label_min_temp , weather?.main?.tempMin.toString())
        TableRow(R.string.label_max_temp , weather?.main?.tempMax.toString())
        TableRow(R.string.label_humidity , weather?.main?.humidity.toString())
        TableRow(R.string.label_wind , weather?.wind?.speed.toString())
    }
}

@Composable
fun TableRow(@StringRes labelRes: Int, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(8.dp)
    ) {
        Text(text = stringResource(labelRes), modifier = Modifier.weight(1f))
        Text(text = value, modifier = Modifier.weight(1f))
    }
}