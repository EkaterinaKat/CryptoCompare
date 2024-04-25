package com.katysh.cryptocompare.domain.entity

data class CoinInfo(
    val price: String?,
    val highDay: String?,
    val lowDay: String?,
    val lastMarket: String?,
    val lastUpdate: String,
    val fromSymbol: String,
    val toSymbol: String?,
    val imageUrl: String
)