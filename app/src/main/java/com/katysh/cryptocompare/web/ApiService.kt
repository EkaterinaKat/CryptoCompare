package com.katysh.cryptocompare.web

import com.katysh.cryptocompare.pojo.CoinInfoResponse
import com.katysh.cryptocompare.pojo.PriceResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_API_KEY) apiKey:String="",
        @Query(QUERY_PARAM_LIMIT) limit:Int=10,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym:String=CURRENCY,
    ):Single<CoinInfoResponse>

    @GET("pricemultifull")
    fun getPriseList(
        @Query(QUERY_PARAM_API_KEY) apiKey:String="",
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms:String=CURRENCY,
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
    ):Single<PriceResponse>

    companion object{
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"

        private const val CURRENCY = "USD"
    }
}