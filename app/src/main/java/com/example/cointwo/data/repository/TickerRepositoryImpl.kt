package com.example.cointwo.data.repository

import android.util.Log
import com.example.cointwo.data.mapper.CoinDataMapper
import com.example.cointwo.data.model.CoinData
import com.example.cointwo.data.model.CoinSocketResponse
import com.example.cointwo.data.model.CoinSymbolResponse
import com.example.cointwo.data.model.common.Currency
import com.example.cointwo.data.remote.CoinHttpApiService
import com.example.cointwo.data.socket.WebSocketService
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(
    private val CoinApiService: CoinHttpApiService
) : TickerRepository {

    private val webSocketService = WebSocketService()
    val _coinListInfo = MutableSharedFlow<List<CoinData>>()

    private val _coinInfo = MutableSharedFlow<CoinSocketResponse>(
        replay = 0,
        extraBufferCapacity = 3,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    var coinInfo = _coinInfo.asSharedFlow()

    private val mapper: CoinDataMapper = CoinDataMapper()

    private var bufferSize = 0
    private lateinit var list: List<CoinSymbolResponse>
    private val mutex = Mutex()

    //여기서 매퍼로 가공 ???
    init {

    }

    override suspend fun observeCoinData(callCoinSymbolList: List<CoinSymbolResponse>): Flow<List<CoinData>> = flow<List<CoinData>> {
        mutex.withLock {
            val coinInfoList = mutableListOf<CoinData>()
            _coinInfo.collect {
                it?.let { data ->
                    coinInfoList.find { cd ->
                        (cd.symbol == data.code.split("-")[1])
                    }?.apply {
                        //업데이트
                        val mapping = mapper.mapperToCoinData(data)
                        currentPrice = mapping.currentPrice
                        rate = mapping.rate
                        volume = mapping.volume
                        decimalCurrentPrice = mapping.decimalCurrentPrice
                        changePricePrevDay = mapping.changePricePrevDay
                        formattedVolume = mapping.formattedVolume
                    } ?: run {
                        //추가
                        val mapping = mapper.mapperToCoinData(data)
                        coinInfoList.add(mapping)
                    }
                    emit(coinInfoList)
                }
            }
        }

    }.flowOn(Dispatchers.IO)


    override suspend fun callCoinSymbolList(): List<CoinSymbolResponse> {
        //300초에 한번식 ???
        return CoinApiService.getCoinSymbolList()
    }

    override suspend fun startWebSocket() {
        withContext(Dispatchers.Default) {
            list = CoinApiService.getCoinSymbolList()
            val result = mutableListOf<String>()
            list.filter {
                it.market.contains("KRW")
            }.forEach {
                result.add(it.market)
            }
            bufferSize = result.size
            Log.d("dodo55 ", "sum111 : ${result}")
            webSocketService.startCoinWebSocket(_coinInfo, result)
        }
    }
}



