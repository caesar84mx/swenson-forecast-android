package com.caesar84mx.swensonforecast.data.model.forecast

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DaysForecast(
    val location: Location,
    val current: Current,
    val forecast: Forecast
) {
    companion object {
        fun default() = DaysForecast(
            Location.default(),
            Current.default(),
            Forecast.default()
        )
    }
}
