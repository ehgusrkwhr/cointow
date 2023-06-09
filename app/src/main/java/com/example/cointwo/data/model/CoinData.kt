package com.example.cointwo.data.model

import com.example.cointwo.data.model.common.Currency

data class CoinData(
    var symbol: String,
    val koreanSymbol: String = "",
    val englishSymbol: String = "",
    var currencyType: Currency,
    var currentPrice: String,
    var decimalCurrentPrice: String,
    var changePricePrevDay: String,
    var rate: String,
    var volume: String,
    var formattedVolume: String,
    var isFavorite: Boolean = false

)
