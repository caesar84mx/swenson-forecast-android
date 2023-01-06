package com.caesar84mx.swensonforecast.ui.main_screen.data

data class CurrentForecastUI(
    val location: String,
    val date: String,
    val iconUrl: String,
    val temperature: String,
    val description: String,
    val windSpeed: String,
    val humidity: String
)