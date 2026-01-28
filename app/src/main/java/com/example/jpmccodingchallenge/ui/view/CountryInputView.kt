import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CountryInputView(
    country: String,
    onCountryChange: (String) -> Unit,
    onSearchClick: (String) -> Unit
) {
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter one of the following:\nCountry or City, or ZIP code",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = country,
            onValueChange = {
                onCountryChange(it)
                if (it.isNotEmpty()) errorMessage = ""
            },
            label = { Text("Enter country/city/zipcode") },
            singleLine = true,
            isError = errorMessage.isNotEmpty()
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (country.isBlank()) {
                    errorMessage = "Please enter a value"
                } else {
                    errorMessage = ""
                    onSearchClick(country)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Find weather")
        }
    }
}