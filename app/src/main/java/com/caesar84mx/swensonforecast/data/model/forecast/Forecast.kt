package com.caesar84mx.swensonforecast.data.model.forecast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    @Json(name = "forecastday") val forecastday: List<ForecastDay>
) {
    companion object {
        fun default() = Forecast(forecastday = listOf(ForecastDay.default()))
    }
}
