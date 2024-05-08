package com.katysh.cryptocompare.domain.repo

import androidx.lifecycle.LiveData
import com.katysh.cryptocompare.domain.entity.CoinInfo

interface CryptoRepo {

    fun getCoinList(): LiveData<List<CoinInfo>>

    fun getCoin(fromSymbol: String): LiveData<CoinInfo>

    fun loadData()
}