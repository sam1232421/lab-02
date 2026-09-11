package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { cityRepository.addCity(it) },   //city name gets sent ftom city listscreen, cityreposity.addCity(it) adds city ti stored city reposity
                        modifier = Modifier.padding(innerPadding),
                        onDeleteCity = { cityRepository.deleteCity(it) } // main gets delete city
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListyCityTheme {
        Greeting("Android")
    }
}

class CityRepository {
    private val _cities = mutableStateListOf( "Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"
    )

    val cities: List<String>
        get() = _cities

    fun addCity(city: String)  {
        _cities.add(city)
    }


    fun deleteCity(city: String){    //delte city same as add but delete and make button
        _cities.remove(city)
    }


}
//whenever red just import it!
@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity: (String) -> Unit,   //string -> unit means cityliststring recieves fun (function) as param
    modifier: Modifier = Modifier,
    onDeleteCity: (String) -> Unit  //delete city feature
) {
    var newCityName by remember { mutableStateOf("") }  //red ask?  , newCityName stores curr text input, and remmebrs the val while composable on screen, and mutable state of is what tells compose to update tje ui when val changes

    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(all = 16.dp)) {
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it },
                label = { Text("City name") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newCityName.isNotBlank()) {
                        onAddCity(newCityName)
                        newCityName = ""
                    }
                }
            ) {
                Text("Add City")
            }

            Button(  //same as add city
                onClick = {
                    if (newCityName.isNotBlank()) {
                        onDeleteCity(newCityName)
                        newCityName = ""
                    }
                }
            ) {
                Text("Delete City")
            }
        }


        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(cities) { city ->
                CityRow(city = city)
            }
        }
    }
}
@Composable
fun CityRow(city: String) {
    Text(
        text = city,
        fontSize = 28.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)

    )
}