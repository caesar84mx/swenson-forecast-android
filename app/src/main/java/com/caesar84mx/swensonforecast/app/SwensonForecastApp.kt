package com.caesar84mx.swensonforecast.app

import android.app.Application
import com.caesar84mx.swensonforecast.di.appModule
import com.caesar84mx.swensonforecast.di.viewModelModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

class SwensonForecastApp: Application() {
    val contextModule = module {
        single { applicationContext }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(contextModule, appModule, viewModelModule) }
    }
}