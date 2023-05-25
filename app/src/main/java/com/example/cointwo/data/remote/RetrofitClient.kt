package com.example.cointwo.data.remote

import com.example.cointwo.data.remote.logging.CustomHttpLogger
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {

//    private const val BASE_URL = "https://api.upbit.com"

//    val coinRetrofit: CoinHttpApiService by lazy {
//        val contentType = "application/json".toMediaType()
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(getOkHttpClient())
//            .addConverterFactory(Json.asConverterFactory(contentType))
//            .build()
//            .create(CoinHttpApiService::class.java)
//    }
//
//    private fun getOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder().apply {
//            val loggingInterceptor = HttpLoggingInterceptor(CustomHttpLogger())
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
////            addInterceptor(HttpLoggingInterceptor())
//            addInterceptor(loggingInterceptor)
//        }.build()
//    }


}