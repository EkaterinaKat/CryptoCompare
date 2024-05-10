package com.katysh.cryptocompare.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.katysh.cryptocompare.domain.entity.CoinInfo
import com.katysh.cryptocompare.domain.usecase.GetCoinListUseCase
import com.katysh.cryptocompare.domain.usecase.GetCoinUseCase
import com.katysh.cryptocompare.domain.usecase.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    getCoinListUseCase: GetCoinListUseCase,
    loadDataUseCase: LoadDataUseCase,
) : ViewModel() {

    val coinInfoList = getCoinListUseCase()

    fun getDetailInfo(fSym: String): LiveData<CoinInfo> {
        Log.i("DI_LOG", "CoinViewModel $this")
        return getCoinUseCase(fSym)
    }

    init {
        loadDataUseCase()
    }
}