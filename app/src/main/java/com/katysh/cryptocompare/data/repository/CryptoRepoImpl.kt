package com.katysh.cryptocompare.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.katysh.cryptocompare.data.database.AppDatabase
import com.katysh.cryptocompare.data.database.CoinInfoDao
import com.katysh.cryptocompare.data.mapper.CoinMapper
import com.katysh.cryptocompare.data.workers.RefreshDataWorker
import com.katysh.cryptocompare.data.workers.RefreshDataWorker.Companion.WORK_NAME
import com.katysh.cryptocompare.domain.entity.CoinInfo
import com.katysh.cryptocompare.domain.repo.CryptoRepo

class CryptoRepoImpl(val application: Application) : CryptoRepo {

    private val dao: CoinInfoDao = AppDatabase.getInstance(application).priceDao()
    private val mapper: CoinMapper = CoinMapper()

    override fun getCoinList(): LiveData<List<CoinInfo>> =
        MediatorLiveData<List<CoinInfo>>().apply {
            addSource(dao.getCoinInfo()) {
                value = it.map { dbm ->
                    mapper.mapDbmToEntity(dbm)
                }
            }
        }

    override fun getCoin(fromSymbol: String): LiveData<CoinInfo> {
        return MediatorLiveData<CoinInfo>().apply {
            addSource(dao.getCoinInfo(fromSymbol)) {
                value = mapper.mapDbmToEntity(it)
            }
        }
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}