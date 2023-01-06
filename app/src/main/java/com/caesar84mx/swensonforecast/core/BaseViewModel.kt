package com.caesar84mx.swensonforecast.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caesar84mx.swensonforecast.app.GlobalEventsProvider
import com.caesar84mx.swensonforecast.data.model.common.Event
import com.caesar84mx.swensonforecast.data.model.common.UIStatus
import com.caesar84mx.swensonforecast.data.model.common.UIStatus.Idle
import com.caesar84mx.swensonforecast.data.model.common.UIStatus.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<D>(private val globalEventsProvider: GlobalEventsProvider): ViewModel() {
    private val _status = MutableStateFlow<UIStatus>(Loading)
    val status: StateFlow<UIStatus>
        get() = _status.asStateFlow()

    fun updateStatus(status: UIStatus) {
        viewModelScope.launch {
            _status.emit(status)
        }
    }

    fun dispatchGlobalEvent(event: Event) {
        globalEventsProvider.dispatchGlobalEvent(event)
    }

    open fun onErrorMessageClose() {

    }

    open fun onBackPressed() { }
}