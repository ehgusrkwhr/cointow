package com.example.cointwo.data.remote

import com.example.cointwo.data.model.CoinSymbolResponse
import retrofit2.http.GET

interface CoinHttpApiService {

    @GET("v1/market/all?isDetails=false")
     suspend fun getCoinSymbolList() : List<CoinSymbolResponse>

    companion object{
         const val BASE_URL = "https://api.upbit.com"
    }
}