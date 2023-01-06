package com.caesar84mx.swensonforecast.di

import com.caesar84mx.swensonforecast.BuildConfig
import com.caesar84mx.swensonforecast.util.providers.GlobalEventsProvider
import com.caesar84mx.swensonforecast.data.networking.adapters.LocalDateAdapter
import com.caesar84mx.swensonforecast.util.providers.DefaultLocationProvider
import com.caesar84mx.swensonforecast.data.networking.adapters.LocalDateTimeAdapter
import com.caesar84mx.swensonforecast.data.networking.interceptors.ApiKeyQueryInterceptor
import com.caesar84mx.swensonforecast.data.networking.repository.ForecastRepository
import com.caesar84mx.swensonforecast.data.networking.services.ForecastService
import com.caesar84mx.swensonforecast.ui.main_screen.viewmodel.MainScreenViewModel
import com.caesar84mx.swensonforecast.util.providers.LocationProvider
import com.caesar84mx.swensonforecast.util.providers.ResourceProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime

val appModule = module {
    single { GlobalEventsProvider() }
    single<LocationProvider> { DefaultLocationProvider(get()) }
    single { ResourceProvider(get()) }
    single<Moshi> {
        Moshi.Builder()
            .add(LocalDateTime::class.java, LocalDateTimeAdapter())
            .add(LocalDate::class.java, LocalDateAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor(ApiKeyQueryInterceptor())
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    single<ForecastService> {
        val retrofit: Retrofit = get()
        retrofit.create(ForecastService::class.java)
    }
    single { ForecastRepository(get()) }
}

val viewModelModule = module {
    viewModel { MainScreenViewModel(get(), get(), get(), get()) }
}