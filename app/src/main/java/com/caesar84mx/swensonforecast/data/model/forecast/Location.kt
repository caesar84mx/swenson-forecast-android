package com.caesar84mx.swensonforecast.data.model.forecast

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class Location(
    val name: String,
    val region: String,
    val country: String,
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double,
    @Json(name = "tz_id") val timeZoneId: String? = null,
    val url: String? = null,
    @Json(name = "localtime_epoch") val localTimeEpoch: Long? = null,
    @Json(name = "localtime") val localTime: LocalDateTime? = null
) {
    companion object {
        fun default() = Location(
            name = "Montevideo",
            region = "Montevideo",
            country = "Uruguay",
            latitude = -34.929075,
            longitude = -56.160620
        )
    }
}
