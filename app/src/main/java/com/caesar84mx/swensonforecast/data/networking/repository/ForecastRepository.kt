package com.caesar84mx.swensonforecast.data.networking.repository

import com.caesar84mx.swensonforecast.data.networking.services.ForecastService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ForecastRepository(private val service: ForecastService) {
    fun currentForecast(query: String) = flow {
        val forecast = service.currentForecast(query = query)
        emit(forecast)
    }.flowOn(Dispatchers.IO)

    fun fetchForecast(query: String, days: Int) = flow {
        val forecast = service.forecast(query = query, days = days)
        emit(forecast)
    }.flowOn(Dispatchers.IO)

    fun fetchLocations(query: String) = flow {
        val locations = service.fetchLocations(query = query)
        emit(locations)
    }.flowOn(Dispatchers.Main)

    companion object {
        val mock = ForecastRepository(
            ForecastService.mock
        )
    }
}