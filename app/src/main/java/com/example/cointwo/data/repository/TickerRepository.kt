package com.example.cointwo.data.repository

import com.example.cointwo.data.model.CoinData
import com.example.cointwo.data.model.CoinSymbolResponse
import kotlinx.coroutines.flow.Flow

interface TickerRepository {

    suspend fun observeCoinData(callCoinSymbolList: List<CoinSymbolResponse>):Flow<List<CoinData>>
    suspend fun callCoinSymbolList() : List<CoinSymbolResponse>
    suspend fun startWebSocket()
}
