package com.example.cointwo.data.socket

import android.util.Log
import com.example.cointwo.data.model.CoinData
import com.example.cointwo.data.model.CoinSocketResponse
import com.example.cointwo.data.model.CoinSymbolResponse
import com.example.cointwo.data.repository.CoinSocketRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.*
import okio.ByteString
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * 코인 실시간 소켓 통신 서비스 클래스
 */
class WebSocketService {

//    @Inject
//    lateinit var coinSocketRepository: CoinSocketRepository

    private lateinit var coinSocketList: MutableList<CoinSocketResponse>
    private var client: OkHttpClient = OkHttpClient()
    private lateinit var webSocket: WebSocket
    private val request by lazy {
        Request.Builder()
            .url("wss://api.upbit.com/websocket/v1")
            .build()
    }

    private lateinit var coinDataFlow: MutableSharedFlow<CoinSocketResponse>
    private lateinit var listCoinData: List<String>
    private var cnt = 0
    private var requestStringData: String = ""
    private val gson = Gson()
    private val uuid = UUID.randomUUID().toString()
    val coinInfoList = mutableListOf<CoinSocketResponse>()
    val mutex = Mutex()

    init {
//        client.newBuilder().pingInterval(10,TimeUnit.SECONDS)
        Log.d("dodo55 ", " WebSocketService 호출")
    }

    fun initCoinList() {
        coinInfoList.clear()
    }

    fun startCoinWebSocket(dataFlow: MutableSharedFlow<CoinSocketResponse>, list: List<String>) {
        coinDataFlow = dataFlow
        listCoinData = list
        coinSocketList = mutableListOf<CoinSocketResponse>()
        webSocket = client.newWebSocket(request, CoinWebSocketListener())

    }

    fun stopCoinWebSocket() {
        webSocket.close(SOCKET_CLOSE_STATUS, null) //없을 경우 끊임없이 서버와 통신함
    }

    fun setRequestSocketData(count: Int) {
        Log.d("dodo55 ", "setRequestSocketData : ${listCoinData[count]} ")
        requestStringData = gson.toJson(
            arrayListOf(
                Ticket(uuid),
                CoinDataForWebSocket(
                    type = "ticker",
                    codes = listOf(listCoinData[count]),
                )
            )
        )
    }

    fun getRequestSocketData() = requestStringData

    inner class CoinWebSocketListener() : WebSocketListener() {
        //        private var coinFlow = dataFlow
        private val gson = Gson()
        private fun getParameter(): String {
            val uuid = UUID.randomUUID().toString()
            Timber.d("dodo uuid : $uuid");
            //모든 코인 리스트를 다 줘야함..

            return gson.toJson(
                arrayListOf(
                    Ticket(uuid),
                    CoinDataForWebSocket(
                        type = "ticker",
                        codes = listCoinData,
                    )
                )
            )
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Timber.d("onClosed call");
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Timber.d("onClosed onClosing");
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Timber.d("onClosed onFailure");
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Timber.d("onClosed onMessage bytes : ${bytes}");
            val result = gson.fromJson(bytes.utf8(), CoinSocketResponse::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                mutex.withLock {
                    coinDataFlow.emit(result)
                }

            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Timber.d("onClosed onMessage text ${text}");
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Timber.d("onClosed onOpen");
            webSocket.send(getParameter())

        }
    }

    companion object {
        const val SOCKET_CLOSE_STATUS = 1001
    }
}

data class Ticket(val ticket: String)
data class Type(val type: String, val codes: List<String>)

data class CoinDataForWebSocket(
    val type: String,
    val codes: List<String>,
//        val isOnlySnapshot : Boolean,
//        val isOnlyRealtime : Boolean

)



