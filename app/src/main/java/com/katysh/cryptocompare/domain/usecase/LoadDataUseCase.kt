package com.katysh.cryptocompare.domain.usecase

import android.util.Log
import com.katysh.cryptocompare.di.ApplicationScope
import com.katysh.cryptocompare.domain.repo.CryptoRepo
import javax.inject.Inject

@ApplicationScope
class LoadDataUseCase @Inject constructor(
    private val repo: CryptoRepo
) {

    operator fun invoke() {
        Log.i("DI_LOG", "LoadDataUseCase $this")
        repo.loadData()
    }

}