package com.katysh.cryptocompare.domain.usecase

import com.katysh.cryptocompare.domain.repo.CryptoRepo

class GetCoinUseCase(
    private val repo: CryptoRepo
) {

    operator fun invoke(fromSymbol: String) = repo.getCoin(fromSymbol)
}