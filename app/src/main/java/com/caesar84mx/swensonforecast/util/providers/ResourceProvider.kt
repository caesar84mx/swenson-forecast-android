package com.caesar84mx.swensonforecast.util.providers

import android.content.Context
import android.icu.util.ULocale
import androidx.annotation.StringRes
import java.util.*

class ResourceProvider(private val context: Context) {
    fun stringResource(@StringRes resource: Int) = context.getString(resource)
    fun stringResource(@StringRes resource: Int, vararg args: Any) = context.getString(resource, *args)
    val currentLocale: Locale
        get() = context.resources.configuration.locale
}