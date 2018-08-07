package ru.belogurow.unsplashclient.api

import okhttp3.OkHttpClient

internal object OkHttpFactory {

    fun createHttpClient(headers: Map<String, String>): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor {
            val requestBuilder = it.request().newBuilder()
            headers.forEach { key, value ->
                requestBuilder.addHeader(key, value)
            }

            it.proceed(requestBuilder.build())
        }

        return httpClient
    }
}