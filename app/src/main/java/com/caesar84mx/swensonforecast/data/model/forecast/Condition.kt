package com.caesar84mx.swensonforecast.data.model.forecast

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Condition(
    val text: String,
    val icon: String,
    val code: Int
) {
    companion object {
        fun default() = Condition(
            "Partly cloudy",
            "//cdn.weatherapi.com/weather/64x64/night/116.png",
            1003
        )
    }
}
