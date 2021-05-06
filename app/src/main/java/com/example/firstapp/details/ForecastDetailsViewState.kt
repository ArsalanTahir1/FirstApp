package com.example.firstapp.details

import com.example.firstapp.api.WeatherDescription

data class ForecastDetailsViewState(val temp:Float,val description: String,val date:String,val iconUrl:String) {
}