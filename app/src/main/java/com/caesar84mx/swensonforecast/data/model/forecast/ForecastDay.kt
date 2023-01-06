package com.caesar84mx.swensonforecast.data.model.forecast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class ForecastDay(
    @Json(name = "date") val date : LocalDate,
    @Json(name = "date_epoch") val dateEpoch : Int,
    @Json(name = "day") val day : Day
) {
    companion object {
        fun default() = ForecastDay(
            date = LocalDate.now(),
            dateEpoch = 13455666,
            day = Day.default()
        )
    }
}