package com.example.cointwo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class CoinSymbolResponse(

    val english_name: String,

    val korean_name: String,

    val market: String
)
