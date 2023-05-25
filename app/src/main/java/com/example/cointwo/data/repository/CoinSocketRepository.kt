package com.example.cointwo.data.repository

import com.example.cointwo.data.model.CoinSocketResponse

interface CoinSocketRepository {

    var list : MutableList<CoinSocketResponse>
    var coinCnt : Int


}