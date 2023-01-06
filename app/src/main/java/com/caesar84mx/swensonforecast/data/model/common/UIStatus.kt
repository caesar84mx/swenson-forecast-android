package com.caesar84mx.swensonforecast.data.model.common

sealed class UIStatus {
    object Idle: UIStatus()
    object Loading: UIStatus()
    data class Error(val message: String): UIStatus()
    data class Success<D>(val data: D): UIStatus()
}
