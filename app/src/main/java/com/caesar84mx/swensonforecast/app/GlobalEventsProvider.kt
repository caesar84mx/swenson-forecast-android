package com.caesar84mx.swensonforecast.app

import com.caesar84mx.swensonforecast.data.model.common.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class GlobalEventsProvider {
    private val _globalEvent = MutableSharedFlow<Event>(extraBufferCapacity = 1)
    val globalEvent: SharedFlow<Event>
        get() = _globalEvent.asSharedFlow()

    fun dispatchGlobalEvent(event: Event) {
        _globalEvent.tryEmit(event)
    }
}