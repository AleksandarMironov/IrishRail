@file:Suppress("DEPRECATION")

package com.aleksandarmironov.irishrail.networking

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

private const val BASE_URL = "http://api.irishrail.ie/realtime/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(SimpleXmlConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

object IrishRailApi {
    val retrofitService : IrishRailApiService by lazy {
        retrofit.create(
            IrishRailApiService::class.java)
    }
}

