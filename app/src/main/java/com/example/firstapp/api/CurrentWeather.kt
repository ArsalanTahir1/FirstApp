package com.example.firstapp.api

import com.squareup.moshi.Json

// Here we re going to define the models needed to model the api response from the weather end point at openweatherapp api

data class Forecast(val temp:Float)
data class Coordinates(val lat:Float, val lan:Float)

data class CurrentWeather(
    //name of the location that was passed in, it will help us display the nme of location in CurrentWeather screen
    val name:String,
    val coord: Coordinates,

    // It defines that there is going to be a Json response field called main and we wanna map that into this forecast property
    @field:Json(name = "main") val forecast: Forecast

)


// the api response for this class, doesn't have a forecast property



