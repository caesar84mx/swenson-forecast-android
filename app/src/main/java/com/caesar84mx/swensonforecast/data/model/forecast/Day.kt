package com.caesar84mx.swensonforecast.data.model.forecast

import com.squareup.moshi.Json

data class Day (
    @Json(name = "maxtemp_c") val maxTempCelsius : Float,
    @Json(name = "maxtemp_f") val maxTempFahrenheit : Float,
    @Json(name = "mintemp_c") val minTempCelsius : Float,
    @Json(name = "mintemp_f") val minTempFahrenheit : Float,
    @Json(name = "avgtemp_c") val avgTempCelsius : Float,
    @Json(name = "avgtemp_f") val avgTempFahrenheit : Float,
    @Json(name = "maxwind_mph") val maxWindMph : Float,
    @Json(name = "maxwind_kph") val maxWindKph : Float,
    @Json(name = "totalprecip_mm") val totalPrecipitationsMm : Float,
    @Json(name = "totalprecip_in") val totalPrecipitationsInches : Float,
    @Json(name = "totalsnow_cm") val totalSnowCm : Float,
    @Json(name = "avgvis_km") val averageVisibilityKm : Float,
    @Json(name = "avgvis_miles") val averageVisibilityMiles : Float,
    @Json(name = "avghumidity") val averageHumidity : Float,
    @Json(name = "daily_will_it_rain") val dailyWillItRain : Int,
    @Json(name = "daily_chance_of_rain") val dailyChanceOfRain : Int,
    @Json(name = "daily_will_it_snow") val dailyWillItSnow : Int,
    @Json(name = "daily_chance_of_snow") val dailyChanceOfSnow : Int,
    @Json(name = "condition") val condition : Condition,
    @Json(name = "uv") val uv : Float
) {
    companion object {
        fun default() = Day(
            maxTempCelsius = 35f,
            maxTempFahrenheit = 55f,
            minTempCelsius = 20f,
            minTempFahrenheit = 40f,
            avgTempCelsius = 30f,
            avgTempFahrenheit = 50f,
            maxWindMph = 1f,
            maxWindKph = 1.6f,
            totalPrecipitationsMm = 0f,
            totalPrecipitationsInches = 0f,
            totalSnowCm = 0f,
            averageVisibilityKm = 16f,
            averageVisibilityMiles = 10f,
            averageHumidity = 80f,
            dailyWillItRain = 0,
            dailyChanceOfRain = 0,
            dailyWillItSnow = 0,
            dailyChanceOfSnow = 0,
            condition = Condition.default(),
            uv = 6f
        )
    }
}