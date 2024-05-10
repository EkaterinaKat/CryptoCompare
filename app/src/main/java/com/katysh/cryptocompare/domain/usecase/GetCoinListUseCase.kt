package com.katysh.cryptocompare.domain.usecase

import android.util.Log
import androidx.lifecycle.LiveData
import com.katysh.cryptocompare.di.ApplicationScope
import com.katysh.cryptocompare.domain.entity.CoinInfo
import com.katysh.cryptocompare.domain.repo.CryptoRepo
import javax.inject.Inject

@ApplicationScope
class GetCoinListUseCase @Inject constructor(
    private val repo: CryptoRepo
) {

    operator fun invoke(): LiveData<List<CoinInfo>> {
        Log.i("DI_LOG", "GetCoinListUseCase $this")
        return repo.getCoinList()
    }
}