package com.katysh.cryptocompare.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.cryptocompare.data.repository.CryptoRepoImpl
import com.katysh.cryptocompare.domain.usecase.GetCoinListUseCase
import com.katysh.cryptocompare.domain.usecase.GetCoinUseCase
import com.katysh.cryptocompare.domain.usecase.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = CryptoRepoImpl(application)

    private val getCoinListUseCase = GetCoinListUseCase(repo)
    private val getCoinUseCase = GetCoinUseCase(repo)
    private val loadDataUseCase = LoadDataUseCase(repo)

    val coinInfoList = getCoinListUseCase()

    fun getDetailInfo(fSym: String) = getCoinUseCase(fSym)

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }
}