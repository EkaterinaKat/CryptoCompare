package com.katysh.cryptocompare.domain.usecase

import com.katysh.cryptocompare.domain.repo.CryptoRepo

class GetCoinListUseCase(
    private val repo: CryptoRepo
) {

    operator fun invoke() = repo.getCoinList()
}