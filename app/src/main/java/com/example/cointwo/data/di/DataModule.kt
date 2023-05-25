package com.example.cointwo.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.cointwo.data.remote.CoinHttpApiService
import com.example.cointwo.data.remote.logging.CustomHttpLogger
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().apply {
            val loggingInterceptor = HttpLoggingInterceptor(CustomHttpLogger())
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
        }.build()
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(CoinHttpApiService.BASE_URL)
//            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }

    @Provides
    @Singleton
    fun provideCoinApiService(retrofit: Retrofit): CoinHttpApiService {
        return retrofit.create(CoinHttpApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}