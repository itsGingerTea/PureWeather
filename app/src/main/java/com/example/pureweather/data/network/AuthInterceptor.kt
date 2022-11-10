package com.example.pureweather.data.network

import com.example.pureweather.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain
            .request()
        val url = original.url.newBuilder()
            .addQueryParameter(KEY, BuildConfig.API_KEY)
            .build()
        val request: Request = original
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val KEY = "key"
    }
}