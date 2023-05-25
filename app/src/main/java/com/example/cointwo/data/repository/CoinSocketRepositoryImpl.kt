package com.example.cointwo.data.repository

import com.example.cointwo.data.model.CoinSocketResponse

class CoinSocketRepositoryImpl : CoinSocketRepository {

    override  var list: MutableList<CoinSocketResponse> = mutableListOf()
    override  var coinCnt: Int = 0

    //리스트 받아서 특정 카운트면 !! emit !!!


}