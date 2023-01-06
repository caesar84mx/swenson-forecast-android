package com.caesar84mx.swensonforecast.data.networking.services

import com.caesar84mx.swensonforecast.data.model.forecast.CurrentForecast
import com.caesar84mx.swensonforecast.data.model.forecast.DaysForecast
import com.caesar84mx.swensonforecast.data.model.forecast.Location
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {
    @GET("current.json")
    suspend fun currentForecast(@Query("q") query: String): CurrentForecast

    @GET("forecast.json")
    suspend fun forecast(@Query("q") query: String, @Query("days") days: Int): DaysForecast

    @GET("search.json")
    suspend fun fetchLocations(@Query("q") query: String): List<Location>

    companion object {
        val mock = object : ForecastService {
            override suspend fun currentForecast(query: String): CurrentForecast = CurrentForecast.default()
            override suspend fun forecast(query: String, days: Int): DaysForecast = DaysForecast.default()
            override suspend fun fetchLocations(query: String): List<Location> = listOf()
        }
    }
}