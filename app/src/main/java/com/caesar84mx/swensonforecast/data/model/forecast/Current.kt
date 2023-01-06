package com.caesar84mx.swensonforecast.data.model.forecast

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentForecast(
    val location: Location,
    val current: Current
) {
    companion object {
        fun default() = CurrentForecast(
            Location.default(),
            Current.default()
        )
    }
}
