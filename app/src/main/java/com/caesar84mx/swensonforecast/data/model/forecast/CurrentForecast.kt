package com.caesar84mx.swensonforecast.data.model.forecast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class Current(
    @Json(name = "last_updated_epoch") val lastUpdatedEpoch: Long,
    @Json(name = "last_updated") val lastUpdated: LocalDateTime,
    @Json(name = "temp_c") val temperatureCelsius: Float,
    @Json(name = "temp_f") val temperatureFahrenheit: Float,
    @Json(name = "is_day") val isDay: Int,
    val condition: Condition,
    @Json(name = "wind_mph") val windMph: Float,
    @Json(name = "wind_kph") val windKph: Float,
    @Json(name = "wind_degree") val windDegree: Int,
    @Json(name = "wind_dir") val windDirection: String,
    @Json(name = "pressure_mb") val pressureMbar: Float,
    @Json(name = "pressure_in") val pressureInches: Float,
    @Json(name = "precip_mm") val precipitationsMm: Float,
    @Json(name = "precip_in") val precipitationsInches: Float,
    val humidity: Int,
    val cloud: Int,
    @Json(name = "feelslike_c") val feelsLikeCelsius: Float,
    @Json(name = "feelslike_f") val feelsLikeFahrenheit: Float,
    @Json(name = "vis_km") val visKm: Float,
    @Json(name = "vis_miles") val visMiles: Float,
    val uv: Float,
    @Json(name = "gust_mph") val gustMph: Float,
    @Json(name = "gust_kph") val gustKph: Float
) {
    companion object {
        fun default() = Current(
            lastUpdatedEpoch = 1672811100,
            lastUpdated = LocalDateTime.now(),
            temperatureCelsius = 21.0f,
            temperatureFahrenheit = 69.8f,
            isDay = 1,
            condition = Condition.default(),
            windMph = 16.1f,
            windKph = 25.9f,
            windDegree = 180,
            windDirection = "S",
            pressureMbar = 1010.0f,
            pressureInches = 29.83f,
            precipitationsMm = 0f,
            precipitationsInches = 0f,
            humidity = 88,
            cloud = 50,
            feelsLikeCelsius = 21f,
            feelsLikeFahrenheit = 69.8f,
            visKm = 10f,
            visMiles = 6f,
            uv = 1f,
            gustMph = 20.4f,
            gustKph = 32.8f
        )
    }
}