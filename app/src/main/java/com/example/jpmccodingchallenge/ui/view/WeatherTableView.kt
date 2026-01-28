package com.example.jpmccodingchallenge.ui.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.jpmccodingchallenge.model.Weather

@Composable
fun WeatherTableView(weather: Weather?) {
    Column(modifier = Modifier.fillMaxWidth()) {

        @Composable
        fun TableRow(label: String, value: String) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
            ) {
                Text(text = label, modifier = Modifier.weight(1f))
                Text(text = value, modifier = Modifier.weight(1f))
            }
        }

        TableRow("Country", weather?.sys?.country ?: "")
        TableRow("City", weather?.cityName ?: "")
        TableRow("Temperature", weather?.main?.temp.toString())
        TableRow("Feels Like", weather?.main?.feelsLike.toString())
        TableRow("Min Temp", weather?.main?.tempMin.toString())
        TableRow("Max Temp", weather?.main?.tempMax.toString())
        TableRow("Humidity", weather?.main?.humidity.toString())
        TableRow("Wind", weather?.wind?.speed.toString())
    }
}