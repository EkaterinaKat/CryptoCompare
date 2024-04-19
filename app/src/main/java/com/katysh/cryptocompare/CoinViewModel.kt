package com.katysh.cryptocompare

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.katysh.cryptocompare.database.AppDatabase
import com.katysh.cryptocompare.pojo.PriceInfo
import com.katysh.cryptocompare.pojo.PriceResponse
import com.katysh.cryptocompare.web.ApiFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    private val db = AppDatabase.getInstance(application)
    val priceList = db.priceDao().getPriceList()

    init {
        loadData()
    }

    fun getDetailInfo(fSym: String):LiveData<PriceInfo> {
        return db.priceDao().getPriceInfoAboutCoin(fSym)
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(",") ?: "nothing" }
            .flatMap { ApiFactory.apiService.getPriseList(fSyms = it) }
            .map { getPriceListFromPriceResponse(it) }
            //благодаря delaySubscription методы repeat и retry будут вызываться с задержкой
            .delaySubscription(10, TimeUnit.SECONDS)
            //repeat заставляет загрузку повторяться бесконечно,
            //но если один раз произойдёт ошибка цикл прервётся
            .repeat()
            //retry заставляет программу продолжать попытки загрузки после неудачной попытки
            //поэтому мы никогда не попадём в блок обработки ошибки
            .retry()
            .subscribeOn(Schedulers.io())
            //на фоновый поток переключаемся, а обратно на главный нет
            //так как в subscribe мы работаем с бд
            .subscribe({
                db.priceDao().insertPriceList(it)
                Log.d("tag251", it.toString())
            }, {
                Log.d("tag251", it.toString())
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromPriceResponse(priceResponse: PriceResponse): List<PriceInfo> {
        val result = ArrayList<PriceInfo>()
        val jsonObject = priceResponse.coinPriceInfoJsonObject ?: return result

        val coinKeySet = jsonObject.keySet() // [BTC,ETH...]
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet() // [USD]
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson<PriceInfo>(
                    currencyJson.getAsJsonObject(currencyKey),  //берём объект лежащий под ключём USD
                    PriceInfo::class.java                       //и превращаем в PriceInfo
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}