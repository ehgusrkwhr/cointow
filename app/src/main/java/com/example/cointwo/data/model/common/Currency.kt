package com.example.cointwo.data.model.common

enum class Currency(val priceNumberOfDigits : Int, val volumeNumberOfDigits : Int) {
    KRW(4,0),
    BTC(8,3)
}