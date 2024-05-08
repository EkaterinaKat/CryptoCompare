package com.katysh.cryptocompare.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.katysh.cryptocompare.data.database.AppDatabase
import com.katysh.cryptocompare.data.database.CoinInfoDao
import com.katysh.cryptocompare.data.mapper.CoinMapper
import com.katysh.cryptocompare.data.web.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    private val params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val apiService = ApiFactory.apiService
    private val dao: CoinInfoDao = AppDatabase.getInstance(context).priceDao()
    private val mapper: CoinMapper = CoinMapper()

    override suspend fun doWork(): Result {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsNames(limit = 50)
                val fSyms = mapper.mapCoinNameListToString(topCoins)
                val coinInfoJsonContainer = apiService.getCoinInfoList(fSyms = fSyms)
                val coinInfoDtoList = mapper.mapJsonContainerToDtoList(coinInfoJsonContainer)
                val coinInfoDbmList = coinInfoDtoList.map { mapper.mapDtoToDbm(it) }
                Log.i("testtest", "coinInfoDbmList $coinInfoDbmList")
                dao.insertPriceList(coinInfoDbmList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }

    companion object {
        const val WORK_NAME = "refresh coin data work"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }
}