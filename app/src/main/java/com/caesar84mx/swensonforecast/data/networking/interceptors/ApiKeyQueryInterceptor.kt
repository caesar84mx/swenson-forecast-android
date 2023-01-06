package com.caesar84mx.swensonforecast.data.networking.interceptors

import com.caesar84mx.swensonforecast.BuildConfig
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class ApiKeyQueryInterceptor: Interceptor {
    override fun intercept(chain: Chain): Response {
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}