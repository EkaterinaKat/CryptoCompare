package com.katysh.cryptocompare.domain.usecase

import com.katysh.cryptocompare.domain.repo.CryptoRepo

class LoadDataUseCase(
    private val repo: CryptoRepo
) {

    operator fun invoke() = repo.loadData()

}