package com.example.cointwo.data.mapper

import android.util.Log
import com.example.cointwo.data.model.CoinData
import com.example.cointwo.data.model.CoinSocketResponse
import com.example.cointwo.data.model.common.Currency
import com.example.cointwo.utils.Logger
import operatorPrefix
import java.text.NumberFormat

class CoinDataMapper {

    fun mapperToCoinData(response: CoinSocketResponse): CoinData {
        return response.run {
            val monetaryUnit = Currency.valueOf(code.split("-")[0])
            var currentPrice = ""
            var changePricePrevDay = ""
            var formattedVolume = ""
//            var formated : Pair<String,Int>

            val pFormat = NumberFormat.getInstance().apply {
                maximumFractionDigits = monetaryUnit.priceNumberOfDigits
            }

            val vFormat = NumberFormat.getInstance().apply {
                maximumFractionDigits = monetaryUnit.volumeNumberOfDigits
            }

            when (monetaryUnit) {
                Currency.KRW -> {
                    currentPrice = pFormat.format(trade_price)
                    changePricePrevDay = pFormat.operatorPrefix(signed_change_price)
                    val dv = (acc_trade_price_24h / 1_000_000).toInt()
                    formattedVolume = vFormat.format(dv)
                    Logger.d("formattedVolume11 : ${formattedVolume}")
                    formattedVolume += if (dv < 1) "원" else "백만"
                    Logger.d("formattedVolume22 : ${formattedVolume}")

                }
                Currency.BTC -> {

                }
            }

            CoinData(
                symbol = code.split("-")[1],
                currencyType = monetaryUnit,
                currentPrice = trade_price.toString(),
                decimalCurrentPrice = currentPrice,
                changePricePrevDay = changePricePrevDay,
                rate = String.format("%.2f", signed_change_rate * 100),
                volume = acc_trade_price_24h.toString(),
                formattedVolume = formattedVolume
            )
        }
    }

}