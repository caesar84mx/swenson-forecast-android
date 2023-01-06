package com.caesar84mx.swensonforecast.ui.main_screen.viewmodel

import android.icu.util.LocaleData
import android.icu.util.LocaleData.MeasurementSystem
import android.icu.util.ULocale
import androidx.lifecycle.viewModelScope
import com.caesar84mx.swensonforecast.R
import com.caesar84mx.swensonforecast.util.providers.GlobalEventsProvider
import com.caesar84mx.swensonforecast.core.BaseViewModel
import com.caesar84mx.swensonforecast.data.model.common.QuitApp
import com.caesar84mx.swensonforecast.data.model.common.UIStatus
import com.caesar84mx.swensonforecast.data.model.common.UIStatus.Loading
import com.caesar84mx.swensonforecast.data.model.common.UIStatus.Success
import com.caesar84mx.swensonforecast.data.model.forecast.Day
import com.caesar84mx.swensonforecast.data.model.forecast.DaysForecast
import com.caesar84mx.swensonforecast.data.model.forecast.ForecastDay
import com.caesar84mx.swensonforecast.data.model.forecast.Location
import com.caesar84mx.swensonforecast.data.networking.repository.ForecastRepository
import com.caesar84mx.swensonforecast.ui.main_screen.data.CurrentForecastUI
import com.caesar84mx.swensonforecast.ui.main_screen.data.DayUI
import com.caesar84mx.swensonforecast.ui.main_screen.data.DaysForecastUI
import com.caesar84mx.swensonforecast.ui.main_screen.data.ForecastDayUI
import com.caesar84mx.swensonforecast.ui.main_screen.data.LocationUI
import com.caesar84mx.swensonforecast.util.formatted
import com.caesar84mx.swensonforecast.util.providers.LocationProvider
import com.caesar84mx.swensonforecast.util.providers.ResourceProvider
import com.caesar84mx.swensonforecast.util.toRelativeDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val locationProvider: LocationProvider,
    private val resourceProvider: ResourceProvider,
    private val repo: ForecastRepository,
    globalEventsProvider: GlobalEventsProvider
) : BaseViewModel<DaysForecastUI>(globalEventsProvider) {
    private val _showSearch = MutableStateFlow(false)
    private val _locations = MutableStateFlow<List<LocationUI>>(listOf())
    private val _query = MutableStateFlow("")

    private var locationQuery: String = ""

    val citySearchQuery = MutableStateFlow("")

    val showSearch: StateFlow<Boolean>
        get() = _showSearch

    val locations: StateFlow<List<LocationUI>>
        get() = _locations

    init {
        locationProvider.listenLocations()
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)

        _query.onEach { query ->
            if (query.isNotEmpty()) {
                fetchForecast(query)
            }
        }.launchIn(viewModelScope)

        locationProvider.currentLocation
            .onEach { location ->
                location?.let {
                    locationQuery = "${it.latitude},${it.longitude}"
                    if (_query.value.isEmpty()) {
                        _query.emit(locationQuery)
                    }
                }
            }
            .catch { error -> handleError(error) }
            .launchIn(viewModelScope)
    }

    override fun onBackPressed() {
        if (_showSearch.value) {
            onSearchDismiss()
        } else {
            dispatchGlobalEvent(QuitApp)
        }

    }

    override fun onCleared() {
        locationProvider.onDispose()
        super.onCleared()
    }

    fun onCityQueryChanged(newValue: String) {
        citySearchQuery.value = newValue

        if (citySearchQuery.value.isEmpty()) {
            _query.value = locationQuery
        } else {
            fetchLocations(newValue)
        }
    }

    fun onLocationPressed(location: LocationUI) {
        viewModelScope.launch {
            citySearchQuery.value = "${location.city} - ${location.state}"
            _query.value = location.city
            _locations.emit(listOf(location))
        }
    }

    fun onSearchPressed() {
        viewModelScope.launch {
            _showSearch.emit(true)
        }
    }

    fun onErasePressed() {
        viewModelScope.launch {
            onCityQueryChanged("")
            _locations.emit(listOf())
        }
    }

    fun onSearchDismiss() {
        viewModelScope.launch {
            _showSearch.emit(false)
        }
    }

    private fun fetchLocations(query: String) {
        viewModelScope.launch {
            repo.fetchLocations(query)
                .flowOn(Dispatchers.IO)
                .collect { locations ->
                    _locations.emit(
                        locations.map { it.toUI() }
                    )
                }
        }
    }

    private fun fetchForecast(query: String) {
        updateStatus(Loading)
        viewModelScope.launch {
            repo.fetchForecast(query, DEFAULT_FORECAST_DAYS_COUNT)
                .flowOn(Dispatchers.IO)
                .catch { error -> handleError(error) }
                .collect {
                    updateStatus(
                        Success(it.toUI())
                    )
                }
        }
    }

    private fun handleError(error: Throwable) {
        updateStatus(
            UIStatus.Error(
                error.message ?: resourceProvider.stringResource(R.string.generic_error_message)
            )
        )
    }

    private fun Day.toUI(): DayUI {
        val measurementSystem = LocaleData.getMeasurementSystem(
            ULocale.forLocale(resourceProvider.currentLocale)
        )

        val temperatureRange = when (measurementSystem) {
            MeasurementSystem.SI -> "${
                resourceProvider.stringResource(
                    R.string.c_format,
                    minTempCelsius
                )
            }/${resourceProvider.stringResource(R.string.c_format, maxTempCelsius)}"
            else -> "${
                resourceProvider.stringResource(
                    R.string.f_format,
                    minTempFahrenheit
                )
            }/${resourceProvider.stringResource(R.string.f_format, maxTempFahrenheit)}"
        }

        return DayUI(
            iconUrl = "https:${condition.icon}",
            temperatureRange = temperatureRange,
        )
    }

    private fun ForecastDay.toUI(): ForecastDayUI = ForecastDayUI(
        day = date.toRelativeDay(),
        dailyForecast = day.toUI()
    )

    private fun DaysForecast.toUI(): DaysForecastUI {
        val measurementSystem = LocaleData.getMeasurementSystem(
            ULocale.forLocale(resourceProvider.currentLocale)
        )

        val temperature = when (measurementSystem) {
            MeasurementSystem.SI -> resourceProvider.stringResource(
                R.string.c_format,
                current.temperatureCelsius
            )
            else -> resourceProvider.stringResource(
                R.string.f_format,
                current.temperatureFahrenheit
            )
        }

        val windSpeed = when (measurementSystem) {
            MeasurementSystem.SI -> resourceProvider.stringResource(
                R.string.kph_format,
                current.windKph
            )
            else -> resourceProvider.stringResource(R.string.mph_format, current.windMph)
        }

        return DaysForecastUI(
            current = CurrentForecastUI(
                location = location.name,
                date = location.localTime?.formatted(DATE_FORMAT) ?: "",
                iconUrl = "https:${current.condition.icon}",
                temperature = temperature,
                description = current.condition.text,
                windSpeed = windSpeed,
                humidity = resourceProvider.stringResource(
                    R.string.humidity_format,
                    current.humidity
                )
            ),
            forecast = forecast.forecastday.map { it.toUI() }
        )
    }

    private fun Location.toUI(): LocationUI = LocationUI(
        city = name,
        state = region
    )

    companion object {
        const val DATE_FORMAT = "EEEE, dd MMM yyyy"
        const val DEFAULT_FORECAST_DAYS_COUNT = 3
    }
}