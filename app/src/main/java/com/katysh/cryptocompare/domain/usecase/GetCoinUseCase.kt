package com.katysh.cryptocompare.domain.usecase

import android.util.Log
import androidx.lifecycle.LiveData
import com.katysh.cryptocompare.di.ApplicationScope
import com.katysh.cryptocompare.domain.entity.CoinInfo
import com.katysh.cryptocompare.domain.repo.CryptoRepo
import javax.inject.Inject

@ApplicationScope
class GetCoinUseCase @Inject constructor(
    private val repo: CryptoRepo
) {

    operator fun invoke(fromSymbol: String): LiveData<CoinInfo> {
        Log.i("DI_LOG", "GetCoinUseCase $this")
        return repo.getCoin(fromSymbol)
    }
}