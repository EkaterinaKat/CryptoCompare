package com.katysh.cryptocompare.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.katysh.cryptocompare.data.database.AppDatabase
import com.katysh.cryptocompare.data.database.CoinInfoDao
import com.katysh.cryptocompare.data.mapper.CoinMapper
import com.katysh.cryptocompare.data.web.ApiFactory
import com.katysh.cryptocompare.domain.entity.CoinInfo
import com.katysh.cryptocompare.domain.repo.CryptoRepo
import kotlinx.coroutines.delay

class CryptoRepoImpl(
    private val application: Application
) : CryptoRepo {

    private val dao: CoinInfoDao = AppDatabase.getInstance(application).priceDao()
    private val mapper: CoinMapper = CoinMapper()

    override fun getCoinList(): LiveData<List<CoinInfo>> =
        MediatorLiveData<List<CoinInfo>>().apply {
            addSource(dao.getCoinInfo()) {
                value = it.map { dbm -> //как это работает?
                    mapper.mapDbmToEntity(dbm)
                }
            }
        }

//    override fun getCoinList(): LiveData<List<CoinInfo>> =
//        MediatorLiveData<List<CoinInfo>>().apply {
//            addSource(dao.getCoinInfo()) {
//                it.map {dbm->
//                    mapper.mapDbmToEntity(dbm)
//                }
//            }
//        }

    override fun getCoin(fromSymbol: String): LiveData<CoinInfo> {
        return MediatorLiveData<CoinInfo>().apply {
            addSource(dao.getCoinInfo(fromSymbol)) {
                value = mapper.mapDbmToEntity(it)
            }
        }
    }

    override suspend fun loadData() {
        while (true) {
            try {
                val topCoins = ApiFactory.apiService.getTopCoinsNames(limit = 50)
                val fSyms = mapper.mapCoinNameListToString(topCoins)
                val coinInfoJsonContainer = ApiFactory.apiService.getCoinInfoList(fSyms = fSyms)
                val coinInfoDtoList = mapper.mapJsonContainerToDtoList(coinInfoJsonContainer)
                val coinInfoDbmList = coinInfoDtoList.map { mapper.mapDtoToDbm(it) }
                Log.i("testtest", "coinInfoDbmList $coinInfoDbmList")
                dao.insertPriceList(coinInfoDbmList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }
}