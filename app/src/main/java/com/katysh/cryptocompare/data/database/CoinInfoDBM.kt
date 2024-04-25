package com.katysh.cryptocompare.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "price_info")
data class CoinInfoDBM(
    @PrimaryKey
    val fromSymbol: String,
    val price: String?,
    val highDay: String?,
    val lowDay: String?,
    val lastMarket: String?,
    val lastUpdate: Long?,
    val toSymbol: String?,
    val imageUrl: String
)