package com.example.cointwo.ui.home.coininfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cointwo.data.model.CoinData
import com.example.cointwo.data.repository.TickerRepository
import com.example.cointwo.data.socket.WebSocketService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CoinInfoFragmentViewModel @Inject constructor(
    private val tickerRepository: TickerRepository
) : ViewModel() {

    private val _coinInfo = MutableStateFlow<List<CoinData>?>(null)
    var coinInfo = _coinInfo.asStateFlow()

    private val webSocketService = WebSocketService()


    init {
        Timber.d("dodo55 ", "팀 팀버")
        Log.d("dodo55 ", "CoinInfoFragmentViewModel 호출")
        getTickerList()

    }
    private val mutex = Mutex()
    private fun getTickerList() {
        viewModelScope.launch {
            tickerRepository.startWebSocket()
            Log.d("dodo55 ", "viewmodel : ${Thread.currentThread()}")
            mutex.withLock {
                tickerRepository.observeCoinData(tickerRepository.callCoinSymbolList()).conflate().collect {
                    it?.let { list ->
                        _coinInfo.value = list.map { cp -> cp.copy() }
//                        _coinInfo.value = list.toMutableList()
                    }
                }
            }
        }
    }

    private fun gtSocketCoinData() {
        viewModelScope.launch {
            tickerRepository.startWebSocket()
        }
    }

}