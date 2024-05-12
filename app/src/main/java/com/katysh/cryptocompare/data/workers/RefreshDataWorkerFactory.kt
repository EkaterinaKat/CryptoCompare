package com.katysh.cryptocompare.data.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.katysh.cryptocompare.data.database.CoinInfoDao
import com.katysh.cryptocompare.data.mapper.CoinMapper
import com.katysh.cryptocompare.data.web.ApiService
import javax.inject.Inject

class RefreshDataWorkerFactory @Inject constructor(
    private val dao: CoinInfoDao,
    private val apiService: ApiService,
    private val mapper: CoinMapper
) : WorkerFactory() {


    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return RefreshDataWorker(appContext, workerParameters, dao, apiService, mapper)
    }
}